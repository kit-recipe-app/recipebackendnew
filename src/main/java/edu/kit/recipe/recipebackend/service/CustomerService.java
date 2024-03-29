package edu.kit.recipe.recipebackend.service;


import edu.kit.recipe.recipebackend.dto.RecipeDTO;
import edu.kit.recipe.recipebackend.entities.Recipe;
import edu.kit.recipe.recipebackend.entities.user.Customer;
import edu.kit.recipe.recipebackend.mapper.RecipeMapper;
import edu.kit.recipe.recipebackend.repository.CustomerRepository;
import edu.kit.recipe.recipebackend.repository.RecipeInfo;
import edu.kit.recipe.recipebackend.repository.RecipeRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * Service for the customer
 */
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;


    /**
     * Retrieves the customer information
     * @param email the email of the customer
     * @return the customer
     */
    public Customer getCustomerInformation(String email) {
        return customerRepository.findByEmail(email).get(0);
    }


    /**
     * Changes the name of the customer
     * @param name the new name
     * @param email the email of the customer
     */
    public void changeCustomerName(@NotNull String name, String email) {
        Customer customer = getCustomerInformation(email);
        customer.setName(name);
        customerRepository.save(customer);
    }


    /**
     * Gets the email of the current-login user
     * @return the email
     */
    @SneakyThrows
    public String getEmail() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            return jwtAuthenticationToken.getTokenAttributes().get("email").toString();
        }
        throw new IllegalAccessException("No email found");
    }


    /**
     * Adds a recipe to the customer
     * @param recipeStored the recipe
     * @param email the email of the customer
     */
    public void addRecipe(Recipe recipeStored, String email) {
        Customer customer = getCustomerInformation(email);
        customer.getRecipes().add(recipeStored);
        customerRepository.save(customer);
    }

    /**
     * Gets all recipes of the customer
     * @param email the email of the customer
     * @return the recipes
     */
    public List<RecipeInfo> getRecipes(String email) {
        return customerRepository.getByEmail(email).getRecipe();
    }

    /**
     * Deletes a user recipe
     * @param email the email of the customer
     * @param id the id of the recipe
     */
    public void deleteRecipe(String email, UUID id) {
        Customer customer = getCustomerInformation(email);
        customer.getRecipes().removeIf(recipe -> recipe.getId().equals(id));
        customerRepository.save(customer);
    }

    /**
     * Updates a recipe
     * @param email the email of the customer
     * @param id the id of the recipe
     * @param recipe the new recipe
     */
    public void updateRecipe(String email, UUID id, RecipeDTO recipe) {
        Optional<Recipe> storedRecipe =  recipeRepository.findById(id);
        if (storedRecipe.isEmpty()) {
            throw new IllegalArgumentException("Recipe not found");
        }

        Customer customerInformation = getCustomerInformation(email);
        if (!customerInformation.getRecipes().contains(storedRecipe.get())) {
            throw new IllegalArgumentException("Recipe not found");
        }
        var newRecipe = recipeMapper.mapRecipeDTOToRecipe(recipe);
        storedRecipe.get().setName(newRecipe.getName());
        storedRecipe.get().setDescription(newRecipe.getDescription());
        storedRecipe.get().setIsPublic(newRecipe.getIsPublic());
        recipeRepository.save(storedRecipe.get());
    }
}
