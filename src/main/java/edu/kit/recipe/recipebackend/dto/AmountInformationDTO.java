package edu.kit.recipe.recipebackend.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AmountInformationDTO(@NotNull @Min(0) double amount, @NotNull @NotEmpty String unit) {
}
