package edu.kit.recipe.recipebackend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for the name
 * @author Johannes Stephan
 */
public record NameDTO(@NotNull @NotEmpty String name) {
}
