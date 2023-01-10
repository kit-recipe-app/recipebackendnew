package edu.kit.recipe.recipebackend.repository;


import edu.kit.recipe.recipebackend.entities.IngredientsWithAmount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientWithAmountRepository extends JpaRepository<IngredientsWithAmount, Long> {

}