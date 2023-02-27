package edu.kit.recipe.recipebackend.service;

import edu.kit.recipe.recipebackend.dto.*;
import edu.kit.recipe.recipebackend.entities.Ingredient;
import edu.kit.recipe.recipebackend.entities.Recipe;
import edu.kit.recipe.recipebackend.entities.image.ImageData;
import edu.kit.recipe.recipebackend.entities.units.Unit;
import edu.kit.recipe.recipebackend.repository.ImageRepository;
import edu.kit.recipe.recipebackend.repository.IngredientRepository;
import edu.kit.recipe.recipebackend.repository.RecipeRepository;
import edu.kit.recipe.recipebackend.repository.UnitRepository;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    private RecipeService recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private CustomerService customerService;


    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private UnitRepository unitRepository;

    @Mock
    private ImageRepository imageRepository;


    @BeforeEach
    void setUp() {
        this.recipeService = new RecipeService(ingredientRepository,
                recipeRepository,
                unitRepository,
                imageRepository,
                customerService);
    }



    @Test
    void deleteRecipe() {
        UUID id = UUID.randomUUID();
        Recipe sample = getSampleRecipe();
        when(recipeRepository.findById(id)).thenReturn(Optional.ofNullable(sample
        ));
        assert sample != null;
        doNothing().when(recipeRepository).delete(sample);
        String result = recipeService.deleteRecipe(String.valueOf(id));
        assertEquals("Recipe deleted", result);
    }

    @Test
    void addRecipe() {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Banane");
        Unit unit = new Unit();
        unit.setName("Stück");

        when(ingredientRepository.findByNameContainsIgnoreCase("Banane")).thenReturn(Optional.of(ingredient));
        when(unitRepository.findByNameContainsIgnoreCase("Stück")).thenReturn(java.util.Optional.of(unit));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(new Recipe());
        String result = recipeService.addRecipe(getSampleRecipeDTO());
        assertEquals("Recipe added" , result);
    }

    @Test
    void addImageToRecipe() {
        Recipe recipe = getSampleRecipe();
        ImageData imageData = new ImageData();
        when(recipeRepository.findById(any(UUID.class))).thenReturn(Optional.of(recipe));
        when(imageRepository.findById(any(UUID.class))).thenReturn(Optional.of(imageData));
        when(recipeRepository.save(any())).thenReturn(null);
        String result = recipeService.addImageToRecipe(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        assertEquals("Image added to recipe", result);
    }



    private RecipeDTO getSampleRecipeDTO() {
        NameDTO vegan = new NameDTO("Vegan");
        CookingInstructionDTO cookingInstructionDTO = new CookingInstructionDTO("TestInstruction");
        IngredientsWithAmountDTO ingredients = new IngredientsWithAmountDTO(
                new IngredientDTO("Banane", vegan),
                new AmountInformationDTO(1, "Stück")
        );
        return new RecipeDTO(
                "Test",
                "TestDescription",
                false,
                List.of(cookingInstructionDTO),
                List.of(ingredients)
        );
    }

    public @NotNull Recipe getSampleRecipe() {
        var recipe = new Recipe();
        recipe.setId(UUID.fromString("939398d1-49a6-4c79-9cdc-c896fedac1e3"));
        recipe.setName("Test");
        recipe.setIngredients(new ArrayList<>());
        recipe.setDescription("Test");
        recipe.setCookingInstructions(new ArrayList<>());
        return recipe;
    }

}