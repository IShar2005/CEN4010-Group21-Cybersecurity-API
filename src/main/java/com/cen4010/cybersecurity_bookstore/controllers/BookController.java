package com.cen4010.cybersecurity_bookstore.controllers;

import com.cen4010.cybersecurity_bookstore.models.Book;
import com.cen4010.cybersecurity_bookstore.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    // Sprint 2: Example GET endpoint to verify database connection
    // GET http://localhost:8080/api/books
    // Returns all books from the database
    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
