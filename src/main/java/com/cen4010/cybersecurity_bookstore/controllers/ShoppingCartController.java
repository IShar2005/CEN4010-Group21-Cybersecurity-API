package com.cen4010.cybersecurity_bookstore.controllers; 

 

import com.cen4010.cybersecurity_bookstore.models.ShoppingCart; 

import com.cen4010.cybersecurity_bookstore.repositories.ShoppingCartRepository; 

import org.springframework.beans.factory.annotation.Autowired; 

import org.springframework.http.ResponseEntity; 

import org.springframework.web.bind.annotation.*; 

import java.util.List; 

 

@RestController 

@RequestMapping("/api/cart") 

public class ShoppingCartController { 

 

    @Autowired 

    private ShoppingCartRepository shoppingCartRepository; 

 

    // GET http://localhost:8080/api/cart 

    // Returns all shopping carts 

    @GetMapping 

    public List<ShoppingCart> getAllCarts() { 

        return shoppingCartRepository.findAll(); 

    } 

 

    // GET http://localhost:8080/api/cart/{userId} 

    // Returns a specific user's cart with all items and book details 

    @GetMapping("/{userId}") 

    public ResponseEntity<ShoppingCart> getCartByUserId(@PathVariable Integer userId) { 

        return shoppingCartRepository.findByUserId(userId) 

                .map(cart -> ResponseEntity.ok(cart)) 

                .orElse(ResponseEntity.notFound().build()); 

    } 

} 