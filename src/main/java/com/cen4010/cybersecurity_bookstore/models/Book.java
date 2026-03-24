package com.cen4010.cybersecurity_bookstore.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

// Sprint 2 - Book Details (Michael Scott)
// This entity is basically a book class. It holds all the details about a book and is the core of my feature.

//Sprint 3 planning:
// I plan to create a function that allows for user input to easily create a book, input the details and store it into the database.
// This will be a crucial feature for filling out the database with information

// Sprint 4:
// I will add input validation so that data inputted into the following fields is checked and incorrect data is rejected.
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
