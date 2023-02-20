package edu.kit.recipe.recipebackend.service;


import edu.kit.recipe.recipebackend.entities.Recipe;
import edu.kit.recipe.recipebackend.entities.user.Customer;
import edu.kit.recipe.recipebackend.repository.CustomerRepository;
import edu.kit.recipe.recipebackend.repository.RecipeInfo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;



    public Customer getCustomerInformation() {
        return customerRepository.findByEmail(getEmail()).get(0);
    }


    public void changeCustomerName(@NotNull String name) {
        Customer customer = getCustomerInformation();
        customer.setName(name);
        customerRepository.save(customer);
    }



    @SneakyThrows
    private String getEmail() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            return jwtAuthenticationToken.getTokenAttributes().get("email").toString();
        }
        throw new IllegalAccessException("No email found");
    }


    public void addRecipe(Recipe recipeStored) {
        Customer customer = getCustomerInformation();
        customer.getRecipes().add(recipeStored);
        customerRepository.save(customer);
    }

    public List<RecipeInfo> getRecipes() {
        return customerRepository.getByEmail(getEmail()).getRecipe();
    }
}
