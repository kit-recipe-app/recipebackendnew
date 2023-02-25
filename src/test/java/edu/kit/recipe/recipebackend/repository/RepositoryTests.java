package edu.kit.recipe.recipebackend.repository;

import edu.kit.recipe.recipebackend.entities.Ingredient;
import edu.kit.recipe.recipebackend.entities.tags.Tag;
import edu.kit.recipe.recipebackend.repository.tag.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;


@DataJpaTest
class RepositoryTests {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private TagRepository tagRepository;

    private Tag veganTag;


    @BeforeEach
    void setUp() {
        var tag = new Tag();
        tag.setName("Vegan");
        this.veganTag = tagRepository.save(tag);
    }


    @Test
    void testIngredientRepository() {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Banana");
        ingredient.setTag(this.veganTag);
        ingredientRepository.save(ingredient);
        assertThat("Ingredient was not saved", ingredientRepository.findAll().size() == 1);

    }
}
