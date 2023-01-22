package edu.kit.recipe.recipebackend.entities.image;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
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
    @Lob
    @Column(length = 1000)
    @Type(type = "org.hibernate.type.ImageType")
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