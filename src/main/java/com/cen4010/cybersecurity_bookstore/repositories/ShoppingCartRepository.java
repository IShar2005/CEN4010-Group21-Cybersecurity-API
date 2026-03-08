package com.cen4010.cybersecurity_bookstore.repositories; 

 

import com.cen4010.cybersecurity_bookstore.models.ShoppingCart; 

import org.springframework.data.jpa.repository.JpaRepository; 

import org.springframework.stereotype.Repository; 

import java.util.Optional; 

 

@Repository 

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> { 

 

    // GET cart by user ID: SELECT * FROM shopping_cart WHERE user_id = ? 

    Optional<ShoppingCart> findByUserId(Integer userId); 

} 