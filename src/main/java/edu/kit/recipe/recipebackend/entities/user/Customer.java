package edu.kit.recipe.recipebackend.entities.user;


import edu.kit.recipe.recipebackend.entities.Recipe;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.*;

@Entity
@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
	@GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true)
    private String email;

    private String name = "";

    @OneToMany(targetEntity = Recipe.class, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Recipe> recipe;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer customer = (Customer) o;
        return id != null && Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public List<Recipe> getRecipes() {
        if (recipe == null) {
            recipe = new ArrayList<>();
        }
        return recipe;
    }
}
