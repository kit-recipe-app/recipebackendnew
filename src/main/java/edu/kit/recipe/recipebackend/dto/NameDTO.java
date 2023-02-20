package edu.kit.recipe.recipebackend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NameDTO(@NotNull @NotEmpty String name) {
}
