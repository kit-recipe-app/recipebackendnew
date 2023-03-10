package edu.kit.recipe.recipebackend.entities;

import edu.kit.recipe.recipebackend.entities.units.Unit;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.util.Objects;
import java.util.UUID;


@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "amount_information")
@Entity
public class AmountInformation {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "amount_id", nullable = false)
    private UUID id;
    @OneToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;
    private double amount;

    public AmountInformation(double amount, Unit unit) {
        this.amount = amount;
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AmountInformation otherAmountInformation = (AmountInformation) o;
        return id != null && Objects.equals(id, otherAmountInformation.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
