package edu.kit.recipe.recipebackend.repository;

import java.util.List;
import java.util.UUID;

/**
 * A Projection for the {@link edu.kit.recipe.recipebackend.entities.Recipe} entity
 */
public interface RecipeInfo {
    UUID getId();

    String getName();

    String getDescription();

    List<CookingInstructionInfo> getCookingInstructions();

    List<IngredientsWithAmountInfo> getIngredients();

    ImageDataInfo1 getImageData();

    /**
     * A Projection for the {@link edu.kit.recipe.recipebackend.entities.CookingInstruction} entity
     */
    interface CookingInstructionInfo {
        String getInstruction();
    }

    /**
     * A Projection for the {@link edu.kit.recipe.recipebackend.entities.IngredientsWithAmount} entity
     */
    interface IngredientsWithAmountInfo {
        AmountInformationInfo getAmountInformation();

        IngredientInfo getIngredient();

        /**
         * A Projection for the {@link edu.kit.recipe.recipebackend.entities.AmountInformation} entity
         */
        interface AmountInformationInfo {
            double getAmount();

            UnitInfo getUnit();

            /**
             * A Projection for the {@link edu.kit.recipe.recipebackend.entities.units.Unit} entity
             */
            interface UnitInfo {
                String getName();
            }
        }

        /**
         * A Projection for the {@link edu.kit.recipe.recipebackend.entities.Ingredient} entity
         */
        interface IngredientInfo {
            String getName();
        }
    }

    /**
     * A Projection for the {@link edu.kit.recipe.recipebackend.entities.image.ImageData} entity
     */
    interface ImageDataInfo1 {
        String getName();
    }
}