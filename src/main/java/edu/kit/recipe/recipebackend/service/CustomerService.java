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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;



    public Customer getCustomerInformation(String email) {
        return customerRepository.findByEmail(email).get(0);
    }


    public void changeCustomerName(@NotNull String name, String email) {
        Customer customer = getCustomerInformation(email);
        customer.setName(name);
        customerRepository.save(customer);
    }



    @SneakyThrows
    public String getEmail() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            return jwtAuthenticationToken.getTokenAttributes().get("email").toString();
        }
        throw new IllegalAccessException("No email found");
    }


    public void addRecipe(Recipe recipeStored, String email) {
        Customer customer = getCustomerInformation(email);
        customer.getRecipes().add(recipeStored);
        customerRepository.save(customer);
    }

    public List<RecipeInfo> getRecipes(String email) {
        return customerRepository.getByEmail(email).getRecipe();
    }

    public void deleteRecipe(String email, UUID id) {
        Customer customer = getCustomerInformation(email);
        customer.getRecipes().removeIf(recipe -> recipe.getId().equals(id));
        customerRepository.save(customer);
    }
}
