package edu.kit.recipe.recipebackend.controller.api.v1;



import edu.kit.recipe.recipebackend.dto.AmountInformationDTO;
import edu.kit.recipe.recipebackend.dto.IngredientDTO;
import edu.kit.recipe.recipebackend.dto.RecipeDTO;
import edu.kit.recipe.recipebackend.entities.AmountInformation;
import edu.kit.recipe.recipebackend.entities.Ingredient;
import edu.kit.recipe.recipebackend.entities.IngredientsWithAmount;
import edu.kit.recipe.recipebackend.entities.Recipe;
import edu.kit.recipe.recipebackend.entities.units.Unit;
import edu.kit.recipe.recipebackend.repository.IngredientRepository;
import edu.kit.recipe.recipebackend.repository.IngredientWithAmountRepository;
import edu.kit.recipe.recipebackend.repository.RecipeRepository;
import edu.kit.recipe.recipebackend.repository.UnitRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Controller
@RequestMapping("/api")
public class RecipeController {


    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final UnitRepository unitRepository;
    private final IngredientWithAmountRepository ingredientWithAmountRepository;

    public RecipeController(IngredientRepository ingredientRepository, RecipeRepository recipeRepository, UnitRepository unitRepository, IngredientWithAmountRepository ingredientWithAmountRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.unitRepository = unitRepository;
        this.ingredientWithAmountRepository = ingredientWithAmountRepository;
    }


    @GetMapping("/ingredients")
    public ResponseEntity<List<Ingredient>> getIngredients() {
        return ResponseEntity.ok(ingredientRepository.findAll());
    }

    @PostMapping("/ingredients")
    public ResponseEntity<Ingredient> addIngredient(@RequestBody IngredientDTO ingredient) {
        if (ingredient.name() == null || ingredient.name().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Ingredient newIngredient = new Ingredient();
        newIngredient.setName(ingredient.name());
        Optional<Ingredient> found =  ingredientRepository.findByNameContainsIgnoreCase(
                ingredient.name()
            );
        if (found.isPresent()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(ingredientRepository.save(newIngredient));
    }


    @DeleteMapping("/ingredients/{id}")
    public ResponseEntity<String> deleteIngredient(@PathVariable String id) {
        Optional<Ingredient> ingredient = ingredientRepository.findById(UUID.fromString(id));
        if (ingredient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        ingredientRepository.delete(ingredient.get());
        return ResponseEntity.ok("Deleted");
    }

    @PostMapping("/recipes")
    public ResponseEntity<Recipe> addRecipe(@RequestBody RecipeDTO recipe) {
        Recipe newRecipe = new Recipe();
        newRecipe.setName(recipe.name());
        return ResponseEntity.ok(recipeRepository.save(newRecipe));
    }




    @GetMapping("/recipes/{id}/ingredients")
    public ResponseEntity<List<IngredientsWithAmount>> getIngredientsForRecipe(@PathVariable String id) {
        Optional<Recipe> recipe = recipeRepository.findById(UUID.fromString(id));
        return recipe.map(value -> ResponseEntity.ok(value.getIngredients()))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }




    @PostMapping("/recipes/{recipeId}/ingredients/{ingredientId}")
    public ResponseEntity<IngredientsWithAmount> addIngredientToRecipe(@PathVariable String recipeId, @RequestBody AmountInformationDTO amount, @PathVariable String ingredientId) {
        Optional<Recipe> recipe = recipeRepository.findById(UUID.fromString(recipeId));
        if (recipe.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<Ingredient> passedIngredient =  ingredientRepository.findById(UUID.fromString(ingredientId));
        if (passedIngredient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Optional<Unit> unit = unitRepository.findByName(amount.unit());
        if (unit.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        IngredientsWithAmount ingredientsWithAmount = new IngredientsWithAmount();
        ingredientsWithAmount.setAmountInformation(new AmountInformation(amount.amount(), unit.get()));
        ingredientsWithAmount.setIngredient(passedIngredient.get());
        recipe.get().getIngredients().add(ingredientsWithAmount);

        ingredientWithAmountRepository.save(ingredientsWithAmount);
        recipeRepository.save(recipe.get());
        return ResponseEntity.ok(ingredientsWithAmount);
    }
}