package com.cen4010.cybersecurity_bookstore.services;

import com.cen4010.cybersecurity_bookstore.models.Book;
import com.cen4010.cybersecurity_bookstore.models.WishList;
import com.cen4010.cybersecurity_bookstore.models.WishListItem;
import com.cen4010.cybersecurity_bookstore.repositories.BookRepository;
import com.cen4010.cybersecurity_bookstore.repositories.WishListItemRepository;
import com.cen4010.cybersecurity_bookstore.repositories.WishListRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WishListService {

    private static final int MAX_WISHLISTS_PER_USER = 3;

    private final WishListRepository wishListRepository;
    private final WishListItemRepository wishListItemRepository;
    private final BookRepository bookRepository;

    public WishListService(WishListRepository wishListRepository,
                           WishListItemRepository wishListItemRepository,
                           BookRepository bookRepository) {
        this.wishListRepository     = wishListRepository;
        this.wishListItemRepository = wishListItemRepository;
        this.bookRepository         = bookRepository;
    }

    // ── 1. Create a wishlist ─────────────────────────────────────────────────
    // Rubric: Given a user Id and a wish list name, create the wishlist.
    public void createWishList(Integer userId, String name) {

        // Max 3 wishlists per user
        int currentCount = wishListRepository.countByUserId(userId);
        if (currentCount >= MAX_WISHLISTS_PER_USER) {
            throw new IllegalStateException(
                "User " + userId + " already has " + MAX_WISHLISTS_PER_USER +
                " wish lists. Delete one before creating a new one.");
        }

        // Name must be unique per user
        if (wishListRepository.existsByUserIdAndName(userId, name)) {
            throw new IllegalStateException(
                "User " + userId + " already has a wish list named \"" + name + "\".");
        }

        WishList wishList = new WishList();
        wishList.setUserId(userId);
        wishList.setName(name);
        wishList.setCreatedAt(LocalDateTime.now());

        wishListRepository.save(wishList);
    }

    // ── 2. Add a book to a wishlist ──────────────────────────────────────────
    // Rubric: Given a book Id and a wish list Id, add the book to that wish list.
    public void addBookToWishList(Integer wishlistId, Integer bookId) {

        // Confirm wishlist exists
        WishList wishList = wishListRepository.findById(wishlistId)
            .orElseThrow(() -> new IllegalArgumentException(
                "Wish list with id " + wishlistId + " not found."));

        // Confirm book exists
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new IllegalArgumentException(
                "Book with id " + bookId + " not found."));

        // Check for duplicate
        if (wishListItemRepository.existsByWishList_WishlistIdAndBook_BookId(wishlistId, bookId)) {
            throw new IllegalStateException(
                "Book " + bookId + " is already in wish list " + wishlistId + ".");
        }

        WishListItem item = new WishListItem();
        item.setWishList(wishList);
        item.setBook(book);
        item.setAddedAt(LocalDateTime.now());

        wishListItemRepository.save(item);
    }

    // ── 3. Remove a book from a wishlist ─────────────────────────────────────
    // Rubric: Given a book Id and a wish list Id, remove the book from that wish list.
    @Transactional
    public void removeBookFromWishList(Integer wishlistId, Integer bookId) {

        // Confirm wishlist exists
        wishListRepository.findById(wishlistId)
            .orElseThrow(() -> new IllegalArgumentException(
                "Wish list with id " + wishlistId + " not found."));

        // Confirm book is actually in the list
        if (!wishListItemRepository.existsByWishList_WishlistIdAndBook_BookId(wishlistId, bookId)) {
            throw new IllegalArgumentException(
                "Book " + bookId + " is not in wish list " + wishlistId + ".");
        }

        wishListItemRepository.deleteByWishList_WishlistIdAndBook_BookId(wishlistId, bookId);
    }

    // ── 4. List all books in a wishlist ──────────────────────────────────────
    // Rubric: Given a wishlist Id, return a list of the books in that wishlist.
    public List<WishListItem> getBooksInWishList(Integer wishlistId) {

        wishListRepository.findById(wishlistId)
            .orElseThrow(() -> new IllegalArgumentException(
                "Wish list with id " + wishlistId + " not found."));

        return wishListItemRepository.findByWishList_WishlistId(wishlistId);
    }
}
