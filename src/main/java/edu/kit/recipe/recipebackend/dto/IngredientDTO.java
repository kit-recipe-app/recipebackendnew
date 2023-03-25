package edu.kit.recipe.recipebackend.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for the ingredient
 * @author Johannes Stephan
 */
public record IngredientDTO(@NotNull @NotEmpty String name,
                            @NotNull TagDTO tag) {
}
