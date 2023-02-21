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

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagsController {

    private final TagRepository tagRepository;


    @PostMapping()
    public ResponseEntity<String> addTag(@Valid @RequestBody NameDTO name) {
        Tag tag = new Tag();
        tag.setName(name.name());
        tagRepository.save(tag);
        return ResponseEntity.ok("Tag added");
    }

    @GetMapping()
    public ResponseEntity<List<TagInfo>> getTags() {
        return ResponseEntity.ok(tagRepository.findAllProjectedBy());
    }
}
