package edu.kit.recipe.recipebackend.controller.exception;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ExceptionController implements ErrorController {
    @RequestMapping("/error")
    public ResponseEntity<String> handleError() {
        return ResponseEntity.notFound().build();
    }
}
