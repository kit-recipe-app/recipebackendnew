package edu.kit.recipe.recipebackend.entities;


import edu.kit.recipe.recipebackend.entities.image.ImageData;
import edu.kit.recipe.recipebackend.entities.tags.Tags;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;

import java.util.*;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "recipe_id")
    private UUID id;
    private String name;


    private String description;


    @OneToMany(targetEntity = CookingInstruction.class, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<CookingInstruction> cookingInstructions;



    @OneToMany(targetEntity = IngredientsWithAmount.class, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<IngredientsWithAmount> ingredients;


    @OneToOne(targetEntity = ImageData.class, cascade = CascadeType.ALL)
    private ImageData imageData;

    @ElementCollection(targetClass = Tags.class)
    @JoinTable(name = "tblInterests", joinColumns = @JoinColumn(name = "recipe_id"))
    @Enumerated(EnumType.STRING)
    private Set<Tags> tags;


    public void addIngredientInformation(IngredientsWithAmount ingredient) {
        if (ingredients == null) {
            ingredients = new ArrayList<>();
        }
        ingredients.add(ingredient);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Recipe recipe = (Recipe) o;
        return id != null && Objects.equals(id, recipe.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void addCookingInstruction(CookingInstruction instruction) {
        if (cookingInstructions == null) {
            cookingInstructions = new ArrayList<>();
        }
        cookingInstructions.add(instruction);
    }
}
