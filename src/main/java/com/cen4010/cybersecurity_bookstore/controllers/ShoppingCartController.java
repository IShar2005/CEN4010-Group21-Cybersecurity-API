package com.cen4010.cybersecurity_bookstore.controllers;

import com.cen4010.cybersecurity_bookstore.models.Book;
import com.cen4010.cybersecurity_bookstore.models.CartItem;
import com.cen4010.cybersecurity_bookstore.models.ShoppingCart;
import com.cen4010.cybersecurity_bookstore.repositories.BookRepository;
import com.cen4010.cybersecurity_bookstore.repositories.CartItemRepository;
import com.cen4010.cybersecurity_bookstore.repositories.ShoppingCartRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    // =========================================================
    // GET http://localhost:8080/api/cart
    // Returns all shopping carts
    // =========================================================
    @GetMapping
    public List<ShoppingCart> getAllCarts() {
        return shoppingCartRepository.findAll();
    }

    // =========================================================
    // GET http://localhost:8080/api/cart/{userId}
    // Returns a specific user's cart with all items
    // =========================================================
    @GetMapping("/{userId}")
    public ResponseEntity<ShoppingCart> getCartByUserId(@PathVariable Integer userId) {
        return shoppingCartRepository.findByUserId(userId)
                .map(cart -> ResponseEntity.ok(cart))
                .orElse(ResponseEntity.notFound().build());
    }

    // =========================================================
    // POST http://localhost:8080/api/cart/{userId}/items
    // Adds a book to the user's cart
    // Request body: { "bookId": 3, "quantity": 1 }
    // =========================================================
    @PostMapping("/{userId}/items")
    @Transactional
    public ResponseEntity<?> addItemToCart(@PathVariable Integer userId, @RequestBody CartItemRequest request) {

        ShoppingCart cart = shoppingCartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    ShoppingCart newCart = new ShoppingCart();
                    newCart.setUserId(userId);
                    newCart.setCreatedAt(LocalDateTime.now());
                    newCart.setCartItems(new ArrayList<>());
                    return shoppingCartRepository.save(newCart);
                });

        Optional<Book> book = bookRepository.findById(request.getBookId());
        if (book.isEmpty()) {
            return ResponseEntity.badRequest().body("Book with ID " + request.getBookId() + " not found");
        }

        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getBook().getBookId().equals(request.getBookId()))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + request.getQuantity());
            cartItemRepository.save(existingItem.get());
        } else {
            CartItem newItem = new CartItem();
            newItem.setShoppingCart(cart);
            newItem.setBook(book.get());
            newItem.setQuantity(request.getQuantity());
            cart.getCartItems().add(newItem);
            cartItemRepository.save(newItem);
        }

        entityManager.flush();
        entityManager.clear();
        return ResponseEntity.ok(shoppingCartRepository.findByUserId(userId).get());
    }

    // =========================================================
    // PUT http://localhost:8080/api/cart/{userId}/items/{bookId}
    // Updates the quantity of a book in the cart
    // Request body: { "quantity": 3 }
    // =========================================================
    @PutMapping("/{userId}/items/{bookId}")
    @Transactional
    public ResponseEntity<?> updateCartItemQuantity(
            @PathVariable Integer userId,
            @PathVariable Integer bookId,
            @RequestBody QuantityUpdateRequest request) {

        Optional<ShoppingCart> cart = shoppingCartRepository.findByUserId(userId);
        if (cart.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<CartItem> item = cart.get().getCartItems().stream()
                .filter(ci -> ci.getBook().getBookId().equals(bookId))
                .findFirst();

        if (item.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (request.getQuantity() <= 0) {
            cart.get().getCartItems().remove(item.get());
            cartItemRepository.delete(item.get());
        } else {
            item.get().setQuantity(request.getQuantity());
            cartItemRepository.save(item.get());
        }

        entityManager.flush();
        entityManager.clear();
        return ResponseEntity.ok(shoppingCartRepository.findByUserId(userId).get());
    }

    // =========================================================
    // DELETE http://localhost:8080/api/cart/{userId}/items/{bookId}
    // Removes a book from the user's cart
    // =========================================================
    @DeleteMapping("/{userId}/items/{bookId}")
    @Transactional
    public ResponseEntity<?> removeItemFromCart(
            @PathVariable Integer userId,
            @PathVariable Integer bookId) {

        Optional<ShoppingCart> cart = shoppingCartRepository.findByUserId(userId);
        if (cart.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<CartItem> item = cart.get().getCartItems().stream()
                .filter(ci -> ci.getBook().getBookId().equals(bookId))
                .findFirst();

        if (item.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        cart.get().getCartItems().remove(item.get());
        cartItemRepository.delete(item.get());

        entityManager.flush();
        entityManager.clear();
        return ResponseEntity.ok(shoppingCartRepository.findByUserId(userId).get());
    }

    // =========================================================
    // Inner classes for request bodies
    // =========================================================
    public static class CartItemRequest {
        private Integer bookId;
        private Integer quantity;

        public Integer getBookId() { return bookId; }
        public void setBookId(Integer bookId) { this.bookId = bookId; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }

    public static class QuantityUpdateRequest {
        private Integer quantity;

        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }
}