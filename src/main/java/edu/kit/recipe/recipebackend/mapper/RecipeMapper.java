package edu.kit.recipe.recipebackend.mapper;


import edu.kit.recipe.recipebackend.dto.CookingInstructionDTO;
import edu.kit.recipe.recipebackend.dto.IngredientsWithAmountDTO;
import edu.kit.recipe.recipebackend.dto.RecipeDTO;
import edu.kit.recipe.recipebackend.entities.*;
import edu.kit.recipe.recipebackend.entities.units.Unit;
import edu.kit.recipe.recipebackend.repository.IngredientRepository;
import edu.kit.recipe.recipebackend.repository.RecipeRepository;
import edu.kit.recipe.recipebackend.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class RecipeMapper {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final UnitRepository unitRepository;



    public Recipe mapRecipeDTOToRecipe(RecipeDTO recipe) {
        Recipe newRecipe = new Recipe();
        newRecipe.setName(recipe.name());
        newRecipe.setDescription(recipe.description());
        for (IngredientsWithAmountDTO ingredientInformation : recipe.ingredients()) {
            Optional<Ingredient> found = ingredientRepository.findByNameContainsIgnoreCase(ingredientInformation.ingredient().name());
            if (found.isEmpty()) {
                throw new IllegalArgumentException("Ingredient not found");
            }
            Optional<Unit> unit = unitRepository.findByNameContainsIgnoreCase(ingredientInformation.amount().unit());
            if (unit.isEmpty()) {
                throw new IllegalArgumentException("Unit not found");
            }
            if (ingredientInformation.amount().amount()<= 0) {
                throw new IllegalArgumentException("Amount is not valid");
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
        return recipeRepository.save(newRecipe);
    }

}
