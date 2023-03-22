package edu.kit.recipe.recipebackend.entities.image;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class ImageData {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private String name;
    private String type;

    //In further development this should be annotated with an imageType to prevent @Transactional Statements
    @Lob
    @Column(length = 100000)
    private byte[] image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ImageData imageData = (ImageData) o;
        return id != null && Objects.equals(id, imageData.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}