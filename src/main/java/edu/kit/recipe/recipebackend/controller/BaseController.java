package edu.kit.recipe.recipebackend.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller for the base page
 * @author Johannes Stephan
 */
@RestController
@RequestMapping("/")
public class BaseController {

    /**
     * Test endpoint to check if the user - registration is working
     * @return a message : "Hello World!" or "Hello <email>!"
     */
    @GetMapping
    public ResponseEntity<String> checkAlive(JwtAuthenticationToken authentication) {
        if (authentication.getTokenAttributes().get("email") == null) {
            return ResponseEntity.ok("Hello World!");
        }
        return ResponseEntity.ok("Hello " + authentication.getTokenAttributes().get("email") + "!");
    }

    /**
     * Test endpoint to check if the API is working
     * @return a message : "Hello World!"
     */
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello World!");
    }
}
