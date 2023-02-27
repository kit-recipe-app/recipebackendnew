package edu.kit.recipe.recipebackend.controller.api.v1.tag;


import edu.kit.recipe.recipebackend.dto.NameDTO;
import edu.kit.recipe.recipebackend.entities.tags.Tag;
import edu.kit.recipe.recipebackend.repository.tag.TagInfo;
import edu.kit.recipe.recipebackend.repository.tag.TagRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for the tags
 * @author Johannes Stephan
 */
@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagsController {

    private final TagRepository tagRepository;


    /**
     * Adds a new tag to the database
     * @param name the name of the tag
     * @return a message that the tag was added
     */
    @PostMapping()
    public ResponseEntity<String> addTag(@Valid @RequestBody NameDTO name) {
        Tag tag = new Tag();
        tag.setName(name.name());
        tagRepository.save(tag);
        return ResponseEntity.ok("Tag added");
    }

    /**
     * Gets all tags that are in the database
     * @return a list of all tags
     */
    @GetMapping()
    public ResponseEntity<List<TagInfo>> getTags() {
        return ResponseEntity.ok(tagRepository.findAllProjectedBy());
    }
}
