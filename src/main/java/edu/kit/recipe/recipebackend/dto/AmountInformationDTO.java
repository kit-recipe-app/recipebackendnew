package edu.kit.recipe.recipebackend.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for the amount information
 * @author Johannes Stephan
 */
public record AmountInformationDTO(@NotNull @Min(0) double amount, @NotNull @NotEmpty String unit) {
}
