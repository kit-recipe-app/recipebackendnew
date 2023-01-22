package edu.kit.recipe.recipebackend.repository;

import java.util.UUID;

/**
 * A Projection for the {@link edu.kit.recipe.recipebackend.entities.image.ImageData} entity
 */
public interface ImageDataInfo {
    UUID getId();

    String getName();
}