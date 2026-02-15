package com.cen4010.cybersecurity_bookstore.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "wish_list")
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    private Integer wishlistId;

    @Column(name = "user_id", nullable = false, unique = true)
    private Integer userId;

    @Column(name = "name")
    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // One wish list has many wish list items
    @OneToMany(mappedBy = "wishList", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<WishListItem> wishListItems;

    // Default constructor
    public WishList() {}

    // Getters and Setters
    public Integer getWishlistId() { return wishlistId; }
    public void setWishlistId(Integer wishlistId) { this.wishlistId = wishlistId; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<WishListItem> getWishListItems() { return wishListItems; }
    public void setWishListItems(List<WishListItem> wishListItems) { this.wishListItems = wishListItems; }
}
