package edu.kit.recipe.recipebackend.controller.api.v1.user;


import edu.kit.recipe.recipebackend.dto.CustomerDTO;
import edu.kit.recipe.recipebackend.dto.NameDTO;
import edu.kit.recipe.recipebackend.entities.user.Customer;
import edu.kit.recipe.recipebackend.repository.RecipeInfo;
import edu.kit.recipe.recipebackend.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * Changes the name of the current logged-in user
     * @param name the new name
     */
    @PutMapping("/name")
    public void changeCustomerName(@RequestBody @Valid NameDTO name) {
        customerService.changeCustomerName(name.name(), customerService.getEmail());
    }
}
