package com.cen4010.cybersecurity_bookstore.controllers;

import com.cen4010.cybersecurity_bookstore.models.Book;
import com.cen4010.cybersecurity_bookstore.repositories.BookRepository;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import javax.management.RuntimeErrorException;





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

    // Sprint 3: Methods for easy book creation and deletion for administrators - Michael Scott
    //  CREATE
    @PostMapping("/create")
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Book savedBook = bookRepository.save(book); 
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED); //Sprint 4 - Input Validation - Michael Scott
    }

    // READ
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Integer id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
    }

    //READ (ISBN)
    @GetMapping("/isbn/{isbn}")
    public Book getBookByISBN(@PathVariable String isbn) {
        return bookRepository.findByIsbn(isbn).orElseThrow(() -> new RuntimeException("Book not find with isbn: " + isbn));
    }

    // UPDATE
    @PutMapping("/edit/{id}")
    public ResponseEntity<Book> updateBook(@Valid @PathVariable Integer id, @RequestBody Book bookDetails) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        book.setTitle(bookDetails.getTitle());
        book.setIsbn(bookDetails.getIsbn());
        book.setDescription(bookDetails.getDescription());
        book.setPrice(bookDetails.getPrice());
        book.setGenre(bookDetails.getGenre());
        book.setPublisher(bookDetails.getPublisher());
        book.setYearPublished(bookDetails.getYearPublished());
        
        //Sprint 4 - Input Validation - Michael Scott
        Book updatedBook = bookRepository.save(book);
        return ResponseEntity.ok(updatedBook);
    }
    
    // DELETE
    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable Integer id){
        bookRepository.deleteById(id);
        return "Book with ID " + id + " has been deleted.";
    }
    
}
