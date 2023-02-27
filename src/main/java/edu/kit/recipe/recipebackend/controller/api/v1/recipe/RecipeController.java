package edu.kit.recipe.recipebackend.controller.api.v1.recipe;


import edu.kit.recipe.recipebackend.dto.RecipeDTO;
import edu.kit.recipe.recipebackend.entities.Recipe;
import edu.kit.recipe.recipebackend.repository.RecipeInfo;
import edu.kit.recipe.recipebackend.repository.RecipeRepository;
import edu.kit.recipe.recipebackend.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * Controller for the recipes
 * @author Johannes Stephan
 */
@RestController
@RequestMapping("/api/v1/recipes")
@RequiredArgsConstructor
@Validated
public class RecipeController {

    private final RecipeRepository recipeRepository;

    private final RecipeService recipeService;

    /**
     * Deletes a recipe from the database
     * @param id the id of the recipe to delete
     * @return the id of the deleted recipe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable String id) {
        return ResponseEntity.ok().body(recipeService.deleteRecipe(id));
    }

    /**
     * Adds a new recipe to the database
     * @param recipe the recipe to add
     * @return the id of the added recipe
     */
    @PostMapping
    public ResponseEntity<String> addRecipe(@RequestBody @Valid RecipeDTO recipe) {
        return ResponseEntity.ok(recipeService.addRecipe(recipe));
    }

    /**
     * Gets all public recipes that are in the database
     * @return a list of all recipes
     */
    @GetMapping
    public ResponseEntity<List<RecipeInfo>> getRecipeNameWithID() {
        return ResponseEntity.ok(recipeRepository.findByIsPublicTrue());
    }

    /**
     * Adds an image to a recipe
     * @param recipeId the id of the recipe
     * @param imageId the id of the image
     * @return a validation message that the image was added.
     */
    @PostMapping("/{recipeId}/image/{imageId}")
    public ResponseEntity<String> addImageToRecipe(@PathVariable String recipeId, @PathVariable String imageId ) {
        return ResponseEntity.ok(recipeService.addImageToRecipe(recipeId, imageId));
    }


    /**
     * Gets the image of a recipe
     * @param recipeId the id of the recipe
     * @return the name of the image
     */
    @GetMapping("/{recipeId}/image")
    public ResponseEntity<String> getImageForRecipe(@PathVariable String recipeId) {
        Optional<Recipe> recipe = recipeRepository.findById(UUID.fromString(recipeId));
        return recipe.map(value -> ResponseEntity.ok(value.getImageData().getName())).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}