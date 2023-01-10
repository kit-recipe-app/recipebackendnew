package edu.kit.recipe.recipebackend.entities;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Table(name = "ingredients_with_amount")
@Entity
public class IngredientsWithAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ingredients_with_amount_id", nullable = false)
    private Long id;


    @OneToOne(targetEntity = AmountInformation.class, cascade = CascadeType.ALL)
    private AmountInformation amountInformation;


    @OneToOne(targetEntity = Ingredient.class, cascade = CascadeType.ALL)
    private Ingredient ingredient;


}
