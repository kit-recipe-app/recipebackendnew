package edu.kit.recipe.recipebackend.controller;


import edu.kit.recipe.recipebackend.TodoEntity;
import edu.kit.recipe.recipebackend.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/")
@RequiredArgsConstructor
public class TestController {
    private final TodoRepository todoRepository;


    @GetMapping("/")
    public String alive() {
        return "alive";
    }

    @GetMapping("/list")
    public List<String> list() {
        return todoRepository.findAll().stream().map(TodoEntity::getName).toList();
    }

    @GetMapping("/add")
    public String add() {
        TodoEntity todoEntity = new TodoEntity();
        todoEntity.setName("test");
        todoRepository.save(todoEntity);
        return "added";
    } 
}
