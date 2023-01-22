package edu.kit.recipe.recipebackend.repository;


import edu.kit.recipe.recipebackend.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
}

