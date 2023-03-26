package edu.kit.recipe.recipebackend.mapper;


import edu.kit.recipe.recipebackend.dto.CookingInstructionDTO;
import edu.kit.recipe.recipebackend.dto.IngredientsWithAmountDTO;
import edu.kit.recipe.recipebackend.dto.RecipeDTO;
import edu.kit.recipe.recipebackend.entities.*;
import edu.kit.recipe.recipebackend.entities.units.Unit;
import edu.kit.recipe.recipebackend.repository.IngredientRepository;
import edu.kit.recipe.recipebackend.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;


/**
 * Mapperclass for Recipes
 */
@Component
@RequiredArgsConstructor
public class RecipeMapper {

    private final IngredientRepository ingredientRepository;
    private final UnitRepository unitRepository;


    /**
     * Maps a RecipeDTO to a Recipe and checks if the ingredients and units are valid
     * @param recipe the RecipeDTO
     * @return the parsed Recipe
     */
    public Recipe mapRecipeDTOToRecipe(RecipeDTO recipe) {
        Recipe newRecipe = new Recipe();
        newRecipe.setName(recipe.name());
        newRecipe.setDescription(recipe.description());
        newRecipe.setCalories(recipe.calories());
        newRecipe.setDifficulty(recipe.difficulty());
        newRecipe.setDurationInMin(recipe.durationInMin());
        for (IngredientsWithAmountDTO ingredientInformation : recipe.ingredients()) {
            Optional<Ingredient> found = ingredientRepository.findTopByNameIgnoreCase(ingredientInformation.ingredient().name());
            if (found.isEmpty()) {
                throw new IllegalArgumentException("Ingredient: " + ingredientInformation.ingredient().name() + " not found");
            }
            Optional<Unit> unit = unitRepository.findByNameContainsIgnoreCase(ingredientInformation.amount().unit());
            if (unit.isEmpty()) {
                throw new IllegalArgumentException("Unit: " + ingredientInformation.amount().unit() + " not found");
            }
            if (ingredientInformation.amount().amount()<= 0) {
                throw new IllegalArgumentException("Amount is negative or zero");
            }
            IngredientsWithAmount newIngredient = new IngredientsWithAmount();
            newIngredient.setIngredient(found.get());
            newIngredient.setAmountInformation(new AmountInformation(ingredientInformation.amount().amount(), unit.get()));
            newRecipe.addIngredientInformation(newIngredient);
        }

        for (CookingInstructionDTO cookingInstruction : recipe.cookingInstructions()) {
            if (cookingInstruction.instruction() == null || cookingInstruction.instruction().isEmpty()) {
                throw new IllegalArgumentException("Instruction is null or empty");
            }
            CookingInstruction newInstruction = new CookingInstruction();
            newInstruction.setInstruction(cookingInstruction.instruction());
            newRecipe.addCookingInstruction(newInstruction);
        }
        if (recipe.isPublic() != null) {
            newRecipe.setIsPublic(recipe.isPublic());
        } else {
            newRecipe.setIsPublic(false);
        }
        return newRecipe;
    }

}
