package edu.kit.recipe.recipebackend.repository;

import edu.kit.recipe.recipebackend.repository.tag.TagInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A Projection for the {@link edu.kit.recipe.recipebackend.entities.Recipe} entity
 */
public interface RecipeInfo {
    UUID getId();

    String getName();

    String getDescription();

    String getDifficulty();

    double getCalories();

    int getDurationInMin();

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
            TagInfo getTag();
        }
    }

    /**
     * A Projection for the {@link edu.kit.recipe.recipebackend.entities.image.ImageData} entity
     */
    interface ImageDataInfo1 {
        String getName();
    }

    default List<String> getTag() {
        List<String> getTags = this.getIngredients().stream().map(ingredientsWithAmountInfo -> ingredientsWithAmountInfo.getIngredient().getTag().getName()).toList();
        List<String> tags = new ArrayList<>();
        if (getTags.contains("Omnivor")) {
            tags.add("Omnivor");
        } else if (getTags.contains("Vegetarisch")) {
            tags.add("Vegetarisch");
        }
        else {
            tags.add("Vegan");
        }
        return tags;
    }
}