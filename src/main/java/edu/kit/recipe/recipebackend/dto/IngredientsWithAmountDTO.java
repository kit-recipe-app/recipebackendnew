package edu.kit.recipe.recipebackend.dto;


import jakarta.validation.constraints.NotNull;

public record IngredientsWithAmountDTO(@NotNull IngredientDTO ingredient,@NotNull AmountInformationDTO amount) {
}
