package edu.kit.recipe.recipebackend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for the cooking instruction
 * @author Johannes Stephan
 */
public record CookingInstructionDTO(@NotNull @NotEmpty String instruction) {
}
