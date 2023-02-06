package edu.kit.recipe.recipebackend.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UnitDTO(@NotNull(message = "Should not be null, trolol") @NotEmpty(message = "should not be empty") String name) {
}
