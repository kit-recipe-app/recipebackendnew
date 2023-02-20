package edu.kit.recipe.recipebackend.dto;



import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RecipeDTO(@NotNull @NotEmpty String name,
                        @NotNull String description,

                        Boolean isPublic,
                        @NotNull @NotEmpty List<@Valid CookingInstructionDTO> cookingInstructions,
                        @NotNull @NotEmpty List<@Valid IngredientsWithAmountDTO> ingredients) { }

