package edu.kit.recipe.recipebackend.controller.api.v1.user;


import edu.kit.recipe.recipebackend.dto.CustomerDTO;
import edu.kit.recipe.recipebackend.dto.NameDTO;
import edu.kit.recipe.recipebackend.dto.RecipeDTO;
import edu.kit.recipe.recipebackend.entities.user.Customer;
import edu.kit.recipe.recipebackend.repository.RecipeInfo;
import edu.kit.recipe.recipebackend.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Controller for the customer
 * @author Johannes Stephan
 */
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;


    /**
     * Gets the customer information of the current logged-in user
     * @return the customer information
     */
    @GetMapping()
    public CustomerDTO getCustomerInformation() {
        Customer customer = customerService.getCustomerInformation(customerService.getEmail());
        return new CustomerDTO(customer.getName(), customer.getEmail());
    }

    /**
     * Gets all recipes of the current logged-in user
     * @return a list of all recipes
     */
    @GetMapping("/recipes")
    public List<RecipeInfo> getCustomerRecipes() {
        return customerService.getRecipes(customerService.getEmail());
    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable String id) {
        customerService.deleteRecipe(customerService.getEmail(), UUID.fromString(id));
        return ResponseEntity.ok("Recipe deleted");
    }

    /**
     * Updates a recipe of the current logged-in user
     * @param recipe the new recipe
     * @param id the id of the recipe to update
     * @return the id of the updated recipe
     */
    @PutMapping("/recipes/{id}")
    public ResponseEntity<String> updateRecipe(@RequestBody @Valid RecipeDTO recipe, @PathVariable String id) {
        customerService.updateRecipe(customerService.getEmail(), UUID.fromString(id), recipe);
        return ResponseEntity.ok("Recipe updated");
    }

    /**
     * Changes the name of the current logged-in user
     * @param name the new name
     */
    @PutMapping("/name")
    public void changeCustomerName(@RequestBody @Valid NameDTO name) {
        customerService.changeCustomerName(name.name(), customerService.getEmail());
    }
}
