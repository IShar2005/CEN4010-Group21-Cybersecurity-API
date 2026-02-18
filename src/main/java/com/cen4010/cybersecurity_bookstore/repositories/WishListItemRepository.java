package com.cen4010.cybersecurity_bookstore.repositories;

import com.cen4010.cybersecurity_bookstore.models.WishListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishListItemRepository extends JpaRepository<WishListItem, Integer> {

    // Get all items in a wishlist
    List<WishListItem> findByWishList_WishlistId(Integer wishlistId);

    // Check if a book is already in a wishlist
    boolean existsByWishList_WishlistIdAndBook_BookId(Integer wishlistId, Integer bookId);

    // Delete a specific book from a wishlist
    void deleteByWishList_WishlistIdAndBook_BookId(Integer wishlistId, Integer bookId);
}
