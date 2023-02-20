package edu.kit.recipe.recipebackend.service;


import edu.kit.recipe.recipebackend.dto.CookingInstructionDTO;
import edu.kit.recipe.recipebackend.dto.IngredientsWithAmountDTO;
import edu.kit.recipe.recipebackend.dto.RecipeDTO;
import edu.kit.recipe.recipebackend.entities.*;
import edu.kit.recipe.recipebackend.entities.image.ImageData;
import edu.kit.recipe.recipebackend.entities.units.Unit;
import edu.kit.recipe.recipebackend.repository.ImageRepository;
import edu.kit.recipe.recipebackend.repository.IngredientRepository;
import edu.kit.recipe.recipebackend.repository.RecipeRepository;
import edu.kit.recipe.recipebackend.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final Logger logger = Logger.getLogger(RecipeService.class.getName());
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final UnitRepository unitRepository;
    private final ImageRepository imageRepository;

    private final CustomerService customerService;


    public String deleteRecipe(String id) {
        Optional<Recipe> recipe = recipeRepository.findById(UUID.fromString(id));
        if (recipe.isEmpty()) {
            throw new IllegalArgumentException("Recipe not found");
        }
        recipe.get().getIngredients().forEach(ingredient -> ingredient.setIngredient(null));
        recipeRepository.delete(recipe.get());
        return "Recipe deleted";
    }

    public String addRecipe(RecipeDTO recipe) {

        Recipe newRecipe = new Recipe();
        newRecipe.setName(recipe.name());
        newRecipe.setDescription(recipe.description());
        for (IngredientsWithAmountDTO ingredientInformation : recipe.ingredients()) {
            Optional<Ingredient> found = ingredientRepository.findByNameContainsIgnoreCase(ingredientInformation.ingredient().name());
            if (found.isEmpty()) {
                logger.warning("Ingredient not found");
                throw new IllegalArgumentException("Ingredient not found");
            }
            Optional<Unit> unit = unitRepository.findByNameContainsIgnoreCase(ingredientInformation.amount().unit());
            if (unit.isEmpty()) {
                logger.warning("Unit not found");
                throw new IllegalArgumentException("Unit not found");
            }
            if (ingredientInformation.amount().amount()<= 0) {
                logger.warning("Amount is not valid");
                throw new IllegalArgumentException("Amount is not valid");
            }
            IngredientsWithAmount newIngredient = new IngredientsWithAmount();
            newIngredient.setIngredient(found.get());
            newIngredient.setAmountInformation(new AmountInformation(ingredientInformation.amount().amount(), unit.get()));
            newRecipe.addIngredientInformation(newIngredient);
        }

        for (CookingInstructionDTO cookingInstruction : recipe.cookingInstructions()) {
            if (cookingInstruction.instruction() == null || cookingInstruction.instruction().isEmpty()) {
                logger.warning("Instruction is null or empty");
                throw new IllegalArgumentException("Instruction is null or empty");
            }
            CookingInstruction newInstruction = new CookingInstruction();
            newInstruction.setInstruction(cookingInstruction.instruction());
            newRecipe.addCookingInstruction(newInstruction);
        }
        newRecipe.setPublic(false);
        var recipeStored = recipeRepository.save(newRecipe);
        customerService.addRecipe(recipeStored);
        return "Recipe added";
    }

    public String addImageToRecipe(String recipeId, String imageId) {
        Optional<Recipe> recipe = recipeRepository.findById(UUID.fromString(recipeId));
        if (recipe.isEmpty()) {
            throw new IllegalArgumentException("Recipe not found");
        }
        Optional<ImageData> image = imageRepository.findById(UUID.fromString(imageId));

        if (image.isEmpty()) {
            throw new IllegalArgumentException("Image not found");
        }
        recipe.get().setImageData(image.get());
        recipeRepository.save(recipe.get());
        return "Image added to recipe";
    }
}
