package edu.kit.recipe.recipebackend.repository;

import edu.kit.recipe.recipebackend.entities.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    CustomerInfo getByEmail(String email);
    List<Customer> findByEmail(String email);


}
