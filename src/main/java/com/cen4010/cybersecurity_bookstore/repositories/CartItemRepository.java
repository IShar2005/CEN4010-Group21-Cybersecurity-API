package com.cen4010.cybersecurity_bookstore.repositories;

import com.cen4010.cybersecurity_bookstore.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    // JpaRepository gives us save(), delete(), findById() automatically
}
