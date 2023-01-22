package edu.kit.recipe.recipebackend.dto;



import java.util.List;

public record RecipeDTO(String name, String description, List<CookingInstructionDTO> cookingInstructions, List<IngredientsWithAmountDTO> ingredients) { }

