package edu.kit.recipe.recipebackend.repository;

import edu.kit.recipe.recipebackend.entities.image.ImageData;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
public interface ImageRepository extends JpaRepository<ImageData, UUID> {
    Optional<ImageData> findByName(String fileName);

    List<ImageDataInfo> findAllProjectedBy();
}
