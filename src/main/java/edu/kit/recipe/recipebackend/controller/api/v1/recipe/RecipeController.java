package edu.kit.recipe.recipebackend.controller.api.v1.recipe;



import edu.kit.recipe.recipebackend.dto.*;
import edu.kit.recipe.recipebackend.entities.*;
import edu.kit.recipe.recipebackend.entities.image.ImageData;
import edu.kit.recipe.recipebackend.entities.units.Unit;
import edu.kit.recipe.recipebackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;


@Controller
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final Logger logger = Logger.getLogger(RecipeController.class.getName());
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final UnitRepository unitRepository;
    private final IngredientWithAmountRepository ingredientWithAmountRepository;

    private final ImageRepository imageRepository;






    @PostMapping
    public ResponseEntity<Recipe> addRecipe(@RequestBody RecipeDTO recipe) {
        Recipe newRecipe = new Recipe();
        if (recipe.name() == null || recipe.name().isEmpty()) {
            logger.warning("Name is null or empty");
            return ResponseEntity.badRequest().build();
        }
        newRecipe.setName(recipe.name());

        if (recipe.description() == null || recipe.description().isEmpty()) {
            logger.warning("Description is null or empty");
            return ResponseEntity.badRequest().build();
        }
        newRecipe.setDescription(recipe.description());

        if (recipe.ingredients() == null || recipe.ingredients().isEmpty()) {
            logger.warning("Ingredients are null or empty");
            return ResponseEntity.badRequest().build();
        }
        for (IngredientsWithAmountDTO ingredientInformation : recipe.ingredients()) {
            Optional<Ingredient> found = ingredientRepository.findByNameContainsIgnoreCase(ingredientInformation.ingredient().name());
            if (found.isEmpty()) {
                logger.warning("Ingredient not found");
                return ResponseEntity.badRequest().build();
            }
            Optional<Unit> unit = unitRepository.findByNameContainsIgnoreCase(ingredientInformation.amount().unit());
            if (unit.isEmpty()) {
                logger.warning("Unit not found");
                return ResponseEntity.badRequest().build();
            }
            if (ingredientInformation.amount().amount()<= 0) {
                logger.warning("Amount is not valid");
                return ResponseEntity.badRequest().build();
            }
            IngredientsWithAmount newIngredient = new IngredientsWithAmount();
            newIngredient.setIngredient(found.get());
            newIngredient.setAmountInformation(new AmountInformation(ingredientInformation.amount().amount(), unit.get()));
            newRecipe.addIngredientInformation(newIngredient);
        }

        for (CookingInstructionDTO cookingInstruction : recipe.cookingInstructions()) {
            if (cookingInstruction.instruction() == null || cookingInstruction.instruction().isEmpty()) {
                logger.warning("Instruction is null or empty");
                return ResponseEntity.badRequest().build();
            }
            CookingInstruction newInstruction = new CookingInstruction();
            newInstruction.setInstruction(cookingInstruction.instruction());
            newRecipe.addCookingInstruction(newInstruction);
        }
        return ResponseEntity.ok(recipeRepository.save(newRecipe));
    }




    @GetMapping("/{id}/ingredients")
    public ResponseEntity<List<IngredientsWithAmount>> getIngredientsForRecipe(@PathVariable String id) {
        Optional<Recipe> recipe = recipeRepository.findById(UUID.fromString(id));
        return recipe.map(value -> ResponseEntity.ok(value.getIngredients()))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping
    public ResponseEntity<List<RecipeInfo>> getRecipeNameWithID() {
        return ResponseEntity.ok(recipeRepository.findAllProjectedBy());
    }




    @PostMapping("/{recipeId}/ingredients/{ingredientId}")
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

    @PostMapping("/{recipeId}/image/{imageId}")
    public ResponseEntity<String> addImageToRecipe(@PathVariable String recipeId, @PathVariable String imageId ) {
        Optional<Recipe> recipe = recipeRepository.findById(UUID.fromString(recipeId));
        if (recipe.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Optional<ImageData> image = imageRepository.findById(UUID.fromString(imageId));

        if (image.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        recipe.get().setImageData(image.get());
        recipeRepository.save(recipe.get());
        return ResponseEntity.ok("Image added to recipe");
    }

    @GetMapping("/{recipeId}/image")
    public ResponseEntity<String> getImageForRecipe(@PathVariable String recipeId) {
        Optional<Recipe> recipe = recipeRepository.findById(UUID.fromString(recipeId));
        return recipe.map(value -> ResponseEntity.ok(value.getImageData().getName())).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}