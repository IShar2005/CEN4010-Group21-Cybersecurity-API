package com.cen4010.cybersecurity_bookstore.controllers;

import com.cen4010.cybersecurity_bookstore.models.WishListItem;
import com.cen4010.cybersecurity_bookstore.services.WishListService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Wish List REST Controller — CEN 4010 Group 21
 *
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ Method  │ Endpoint                                  │ Description       │
 * ├─────────────────────────────────────────────────────────────────────────┤
 * │ POST    │ /api/wishlist                             │ Create wishlist   │
 * │ POST    │ /api/wishlist/{wishlistId}/books/{bookId} │ Add book          │
 * │ DELETE  │ /api/wishlist/{wishlistId}/books/{bookId} │ Remove book       │
 * │ GET     │ /api/wishlist/{wishlistId}/books          │ List books        │
 * └─────────────────────────────────────────────────────────────────────────┘
 */
@RestController
@RequestMapping("/api/wishlist")
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    // ── POST /api/wishlist?userId=1&name=MyList ───────────────────────────────
    // Rubric: Given a user Id and a wish list name, create the wishlist.
    // Response: None (201 Created)
    @PostMapping
    public ResponseEntity<String> createWishList(@RequestParam Integer userId,
                                                 @RequestParam String name) {
        try {
            wishListService.createWishList(userId, name);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // ── POST /api/wishlist/{wishlistId}/books/{bookId} ────────────────────────
    // Rubric: Given a book Id and a wish list Id, add the book to that wish list.
    // Response: None (201 Created)
    @PostMapping("/{wishlistId}/books/{bookId}")
    public ResponseEntity<String> addBook(@PathVariable Integer wishlistId,
                                          @PathVariable Integer bookId) {
        try {
            wishListService.addBookToWishList(wishlistId, bookId);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ── DELETE /api/wishlist/{wishlistId}/books/{bookId} ──────────────────────
    // Rubric: Given a book Id and a wish list Id, remove the book from that wish list.
    // Response: None (204 No Content)
    @DeleteMapping("/{wishlistId}/books/{bookId}")
    public ResponseEntity<String> removeBook(@PathVariable Integer wishlistId,
                                             @PathVariable Integer bookId) {
        try {
            wishListService.removeBookFromWishList(wishlistId, bookId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // ── GET /api/wishlist/{wishlistId}/books ──────────────────────────────────
    // Rubric: Given a wishlist Id, return a list of the books in that wishlist.
    // Response: JSON list of books
    @GetMapping("/{wishlistId}/books")
    public ResponseEntity<?> getBooks(@PathVariable Integer wishlistId) {
        try {
            List<WishListItem> items = wishListService.getBooksInWishList(wishlistId);
            return ResponseEntity.ok(items);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
