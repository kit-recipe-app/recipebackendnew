package edu.kit.recipe.recipebackend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record TagDTO (@NotNull @Pattern(regexp = "Vegan|Vegetarisch|Omnivor") String name) { }