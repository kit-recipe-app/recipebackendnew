package edu.kit.recipe.recipebackend.entities;


import edu.kit.recipe.recipebackend.entities.tags.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "ingredient_id")
    private UUID id;

    private String name;

    @OneToOne(targetEntity = Tag.class)
    private Tag tag;

}
