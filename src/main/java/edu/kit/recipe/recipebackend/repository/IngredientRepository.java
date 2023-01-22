package edu.kit.recipe.recipebackend.repository;


import edu.kit.recipe.recipebackend.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
    Optional<Ingredient> findByNameContainsIgnoreCase(@NonNull String name);
}

