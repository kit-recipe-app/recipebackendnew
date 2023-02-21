package edu.kit.recipe.recipebackend.controller.api.v1.ingredients;


import edu.kit.recipe.recipebackend.dto.IngredientDTO;
import edu.kit.recipe.recipebackend.entities.Ingredient;
import edu.kit.recipe.recipebackend.entities.tags.Tag;
import edu.kit.recipe.recipebackend.repository.IngredientRepository;
import edu.kit.recipe.recipebackend.repository.tag.TagRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ingredients")
@RequiredArgsConstructor
public class IngredientsController {
    private final IngredientRepository ingredientRepository;
    private final TagRepository tagRepository;

    @GetMapping
    public ResponseEntity<List<Ingredient>> getIngredients() {
        return ResponseEntity.ok(ingredientRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Ingredient> addIngredient(@RequestBody @Valid IngredientDTO ingredient) {
        Ingredient newIngredient = new Ingredient();
        newIngredient.setName(ingredient.name());
        Optional<Tag> tag = tagRepository.findByNameIgnoreCase(ingredient.tag().name());
        Optional<Ingredient> found =  ingredientRepository.findByNameContainsIgnoreCase(
                ingredient.name()
            );
        if (found.isPresent() || tag.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        newIngredient.setTag(tag.get());
        return ResponseEntity.ok(ingredientRepository.save(newIngredient));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIngredient(@PathVariable String id) {
        Optional<Ingredient> ingredient = ingredientRepository.findById(UUID.fromString(id));
        if (ingredient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        ingredientRepository.delete(ingredient.get());
        return ResponseEntity.ok("Deleted");
    }
}
