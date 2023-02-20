package edu.kit.recipe.recipebackend.controller.api.v1.user;


import edu.kit.recipe.recipebackend.dto.CustomerDTO;
import edu.kit.recipe.recipebackend.entities.user.Customer;
import edu.kit.recipe.recipebackend.repository.RecipeInfo;
import edu.kit.recipe.recipebackend.service.CustomerService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void changeCustomerName(@NotNull @NotEmpty String name) {
        customerService.changeCustomerName(name);
    }
}
