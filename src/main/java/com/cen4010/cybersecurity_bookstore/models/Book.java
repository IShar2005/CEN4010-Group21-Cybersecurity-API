package com.cen4010.cybersecurity_bookstore.models;

<<<<<<< HEAD
<<<<<<< HEAD
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
=======
>>>>>>> origin/main
=======
import jakarta.validation.constraints.*;
>>>>>>> beeecaf964b92ea40d3f2c8953897ab11b539899
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//Sprint 2 - Book Browsing Feature
//This entity shows a book in the system and is basically the core for my book browsing feature.
//It supports the operations like browsing books by genre, top-sellers, and ratings
//These fields are used by the GET endpoints that were implemented in this sprint.

//This entity connects other domains like authors and publishers. By having it defined well
//it allows me to keep the browsing and sorting logic simple and efficient

//Sprint 3 planning:
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> beeecaf964b92ea40d3f2c8953897ab11b539899
// I plan to create a function that allows for user input to easily create a book, input the details and store it into the database.
// This will be a crucial feature for filling out the database with information

// Sprint 4:
// I will add input validation so that data inputted into the following fields is checked and incorrect data is rejected.
<<<<<<< HEAD
=======
=======


>>>>>>> beeecaf964b92ea40d3f2c8953897ab11b539899
//This entity is going to be expanded through relationships with entities like Rating and Comment
//to support average ratings and reviews from the user. Also, the relationship with Publisher
//will be used to implement the publisher based discount

//Entity relationships:
//each book is associated with a single author and publosher
//a book can have many ratings and comments

<<<<<<< HEAD
>>>>>>> origin/main
=======
>>>>>>> beeecaf964b92ea40d3f2c8953897ab11b539899
@Entity
@Table(name = "book")
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "isbn", nullable = false, unique = true)
    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "^(97(8|9))?\\d{10}$", message = "Invalid Format") 
    private String isbn;

    @Column(name = "title", nullable = false)
    @NotBlank(message = "Title cannot be empty")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    @NotBlank(message = "Description cannot be empty")
    private String description;

    @Column(name = "price", nullable = false)
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be greater or equal to zero")
    private BigDecimal price;

    @Column(name = "genre")
    @NotBlank(message = "Must have a genre")
    private String genre;

    @Column(name = "publisher")
    @NotBlank(message = "Must have a publisher")
    private String publisher;

    @Column(name = "year_published")
    @NotNull(message = "Must have a year published")
    @Min(value = 1000, message = "Year must be valid")
    @Max(value = 2026, message = "Year must be vaild")
    private Integer yearPublished;

    @Column(name = "copies_sold")
    private Integer copiesSold;

    @Column(name = "average_rating")
    private BigDecimal averageRating;

    // Default constructor (required by JPA)
    public Book() {}

    // Getters and Setters
    public Integer getBookId() { return bookId; }
    public void setBookId(Integer bookId) { this.bookId = bookId; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public Integer getYearPublished() { return yearPublished; }
    public void setYearPublished(Integer yearPublished) { this.yearPublished = yearPublished; }

    public Integer getCopiesSold() { return copiesSold; }
    public void setCopiesSold(Integer copiesSold) { this.copiesSold = copiesSold; }

    public BigDecimal getAverageRating() { return averageRating; }
    public void setAverageRating(BigDecimal averageRating) { this.averageRating = averageRating; }
}
