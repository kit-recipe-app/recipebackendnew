package edu.kit.recipe.recipebackend.dto;



import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RecipeDTO(@NotNull @NotEmpty String name, @NotNull String description,@NotNull @NotEmpty List<CookingInstructionDTO> cookingInstructions,@NotNull @NotEmpty List<IngredientsWithAmountDTO> ingredients) { }

