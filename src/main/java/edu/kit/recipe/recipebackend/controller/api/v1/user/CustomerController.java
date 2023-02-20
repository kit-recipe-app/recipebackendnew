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

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class CustomerController {


    private final CustomerService customerService;



    @GetMapping()
    public CustomerDTO getCustomerInformation() {
        Customer customer = customerService.getCustomerInformation();
        return new CustomerDTO(customer.getName(), customer.getEmail());
    }

    @GetMapping("/recipes")
    public List<RecipeInfo> getCustomerRecipes() {
        return customerService.getRecipes();
    }


    @PutMapping("/name")
    public void changeCustomerName(@RequestBody @Valid NameDTO name) {
        customerService.changeCustomerName(name.name());
    }
}
