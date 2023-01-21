package edu.kit.recipe.recipebackend.entities;


import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


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
