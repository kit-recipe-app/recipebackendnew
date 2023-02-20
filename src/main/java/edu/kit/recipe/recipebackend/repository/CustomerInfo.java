package edu.kit.recipe.recipebackend.repository;

import java.util.List;

/**
 * A Projection for the {@link edu.kit.recipe.recipebackend.entities.user.Customer} entity
 */
public interface CustomerInfo {
    List<RecipeInfo> getRecipe();
}