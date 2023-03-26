package edu.kit.recipe.recipebackend.service;


import edu.kit.recipe.recipebackend.dto.RecipeDTO;
import edu.kit.recipe.recipebackend.entities.Recipe;
import edu.kit.recipe.recipebackend.entities.image.ImageData;
import edu.kit.recipe.recipebackend.mapper.RecipeMapper;
import edu.kit.recipe.recipebackend.repository.ImageRepository;
import edu.kit.recipe.recipebackend.repository.RecipeInfo;
import edu.kit.recipe.recipebackend.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final ImageRepository imageRepository;

    private final CustomerService customerService;

    private final RecipeMapper recipeMapper;


    public String deleteRecipe(String id) {
        Optional<Recipe> recipe = recipeRepository.findById(UUID.fromString(id));
        if (recipe.isEmpty()) {
            throw new IllegalArgumentException("Recipe not found");
        }
        recipe.get().getIngredients().forEach(ingredient -> ingredient.setIngredient(null));
        recipeRepository.delete(recipe.get());
        return "Recipe deleted";
    }

    public String addRecipe(RecipeDTO recipe) {
        var recipeStored = recipeRepository.save(recipeMapper.mapRecipeDTOToRecipe(recipe));
        customerService.addRecipe(recipeStored, customerService.getEmail());
        return recipeStored.getId().toString();
    }

    public String addImageToRecipe(String recipeId, String imageId) {
        Optional<Recipe> recipe = recipeRepository.findById(UUID.fromString(recipeId));
        if (recipe.isEmpty()) {
            throw new IllegalArgumentException("Recipe not found");
        }
        Optional<ImageData> image = imageRepository.findById(UUID.fromString(imageId));

        if (image.isEmpty()) {
            throw new IllegalArgumentException("Image not found");
        }
        recipe.get().setImageData(image.get());
        recipeRepository.save(recipe.get());
        return "Image added to recipe";
    }

    public List<RecipeInfo> getSaisonalRecipes() {
        List<RecipeInfo> allRecipes = recipeRepository.findByIsPublicTrue();
        Collections.shuffle(allRecipes);
        return allRecipes.subList(0, Math.min(5, allRecipes.size()));
    }

    public List<RecipeInfo> getRecommendedRecipes() {
        List<RecipeInfo> allRecipes = recipeRepository.findByIsPublicTrue();
        Collections.shuffle(allRecipes);
        return allRecipes.subList(0, Math.min(5, allRecipes.size()));
    }

}
