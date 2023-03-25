package edu.kit.recipe.recipebackend.dto;


import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

/**
 * DTO for the recipe
 *
 * @author Johannes Stephan
 */
public record RecipeDTO(@NotNull @NotEmpty String name,
                        @NotNull String description,

                        Boolean isPublic,
                        @NotNull @Min(0) Integer durationInMin,
                        @NotNull @Min(0) Double calories,
                        @NotNull @Pattern(regexp = "leicht|mittel|schwer") String difficulty,
                        @NotNull @NotEmpty List<@Valid CookingInstructionDTO> cookingInstructions,
                        @NotNull @NotEmpty List<@Valid IngredientsWithAmountDTO> ingredients) {
}

