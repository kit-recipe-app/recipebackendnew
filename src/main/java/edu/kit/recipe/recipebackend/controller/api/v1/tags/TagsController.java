package edu.kit.recipe.recipebackend.controller.api.v1.tags;


import edu.kit.recipe.recipebackend.entities.tags.Tags;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
public class TagsController {


    @GetMapping
    public ResponseEntity<List<String>> getAllTags() {
        return ResponseEntity.ok(Arrays.stream(Tags.values()).map(Enum::name).toList());
    }

}
