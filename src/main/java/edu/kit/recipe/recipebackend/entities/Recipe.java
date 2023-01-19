package edu.kit.recipe.recipebackend.entities;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "recipe_id")
    private Long id;
    private String name;


    private String description;


    @OneToMany(targetEntity = CookingInstruction.class, cascade = CascadeType.ALL)
    private List<CookingInstruction> cookingInstructions;



    @OneToMany(targetEntity = IngredientsWithAmount.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "recipe_foreign_key", referencedColumnName = "recipe_id")
    @ToString.Exclude
    private List<IngredientsWithAmount> ingredients;


    public String getName() {
        return name;
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
}
