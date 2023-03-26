package edu.kit.recipe.recipebackend.controller.exception;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Controller for the error page
 * @author Johannes Stephan
 */
@Controller
public class ExceptionController implements ErrorController {
    @RequestMapping("/error")
    public ResponseEntity<String> handleError() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Page not found");
    }
}
