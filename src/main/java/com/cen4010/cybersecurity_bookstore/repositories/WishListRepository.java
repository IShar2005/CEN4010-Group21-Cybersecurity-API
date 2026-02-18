package com.cen4010.cybersecurity_bookstore.repositories;

import com.cen4010.cybersecurity_bookstore.models.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<WishList, Integer> {

    // Get all wishlists belonging to a user
    List<WishList> findByUserId(Integer userId);

    // Check if a user already has a wishlist with this name
    boolean existsByUserIdAndName(Integer userId, String name);

    // Count how many wishlists a user has (max 3 allowed)
    int countByUserId(Integer userId);
}
