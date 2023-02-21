package edu.kit.recipe.recipebackend.repository.tag;

import edu.kit.recipe.recipebackend.entities.tags.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
    List<TagInfo> findAllProjectedBy();
    Optional<Tag> findByNameIgnoreCase(String name);
}
