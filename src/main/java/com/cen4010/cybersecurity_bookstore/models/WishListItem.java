package com.cen4010.cybersecurity_bookstore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "wish_list_item")
public class WishListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_item_id")
    private Integer wishlistItemId;

    // Many wish list items belong to one wish list
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wishlist_id", nullable = false)
    @JsonIgnore
    private WishList wishList;

    // Each wish list item references a book
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "added_at")
    private LocalDateTime addedAt;

    // Default constructor
    public WishListItem() {}

    // Getters and Setters
    public Integer getWishlistItemId() { return wishlistItemId; }
    public void setWishlistItemId(Integer wishlistItemId) { this.wishlistItemId = wishlistItemId; }

    public WishList getWishList() { return wishList; }
    public void setWishList(WishList wishList) { this.wishList = wishList; }

    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }

    public LocalDateTime getAddedAt() { return addedAt; }
    public void setAddedAt(LocalDateTime addedAt) { this.addedAt = addedAt; }
}
