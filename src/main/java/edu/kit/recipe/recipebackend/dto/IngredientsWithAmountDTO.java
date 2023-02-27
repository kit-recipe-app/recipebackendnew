package edu.kit.recipe.recipebackend.dto;


import jakarta.validation.constraints.NotNull;

/**
 * DTO for the ingredients with amount
 * @author Johannes Stephan
 */
public record IngredientsWithAmountDTO(@NotNull IngredientDTO ingredient,@NotNull AmountInformationDTO amount) {
}
