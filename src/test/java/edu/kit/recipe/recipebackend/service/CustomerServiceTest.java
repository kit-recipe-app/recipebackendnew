package edu.kit.recipe.recipebackend.service;

import edu.kit.recipe.recipebackend.entities.Recipe;
import edu.kit.recipe.recipebackend.entities.user.Customer;
import edu.kit.recipe.recipebackend.mapper.RecipeMapper;
import edu.kit.recipe.recipebackend.repository.CustomerRepository;
import edu.kit.recipe.recipebackend.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    private static final String EMAIL = "test@test.com";

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeMapper recipeMapper;

    private CustomerService customerService;


    @BeforeEach
    void setUp() {
        customerService = new CustomerService(customerRepository, recipeRepository, recipeMapper);
    }

    @Test
    void getCustomerInformation() {
        mockCustomerInformation();
        customerService.getCustomerInformation(EMAIL);
        verify(customerRepository).findByEmail(EMAIL);
    }

    @Test
    void changeCustomerName() {
        mockCustomerInformation();
        customerService.changeCustomerName("newTest", EMAIL);
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(customerArgumentCaptor.capture());
        assertEquals("newTest", customerArgumentCaptor.getValue().getName());
    }

    @Test
    void addRecipe() {
        mockCustomerInformation();
        customerService.addRecipe(getSampleRecipe(), EMAIL);
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(customerArgumentCaptor.capture());
        assertTrue(customerArgumentCaptor.getValue().getRecipes().contains(getSampleRecipe()));
    }

    @Test
    void getRecipes() {
        mockGetByEmail();
        assertEquals(new ArrayList<>(), customerService.getRecipes(EMAIL));
    }

    @Test
    void getEmail() {
        JwtAuthenticationToken auth = mock(JwtAuthenticationToken.class);
        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);
        when(auth.getTokenAttributes()).thenReturn(Map.of("email", EMAIL));
        assertEquals(EMAIL, customerService.getEmail());
    }

    private void mockCustomerInformation() {
        when(customerRepository.findByEmail(EMAIL))
                .thenReturn(List.of(new Customer(UUID.randomUUID(), EMAIL, "Test", new ArrayList<>())));
    }

    private void mockGetByEmail() {
        when(customerRepository.getByEmail(EMAIL))
                .thenReturn(ArrayList::new);
    }


    private Recipe getSampleRecipe() {
        var recipe = new Recipe();
        recipe.setId(UUID.fromString("939398d1-49a6-4c79-9cdc-c896fedac1e3"));
        recipe.setName("Test");
        recipe.setIngredients(new ArrayList<>());
        recipe.setDescription("Test");
        recipe.setCookingInstructions(new ArrayList<>());
        return recipe;
    }

}