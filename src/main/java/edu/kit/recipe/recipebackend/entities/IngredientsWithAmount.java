package edu.kit.recipe.recipebackend.entities;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.UUID;


@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Table(name = "ingredients_with_amount")
@Entity
public class IngredientsWithAmount {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "ingredients_with_amount_id", nullable = false)
    private UUID id;


    @OneToOne(targetEntity = AmountInformation.class, cascade = CascadeType.ALL)
    private AmountInformation amountInformation;


    @OneToOne(targetEntity = Ingredient.class, cascade = CascadeType.ALL)
    private Ingredient ingredient;


}
