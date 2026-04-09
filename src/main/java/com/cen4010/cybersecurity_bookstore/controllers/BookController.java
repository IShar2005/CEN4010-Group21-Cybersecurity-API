package com.cen4010.cybersecurity_bookstore.controllers;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cen4010.cybersecurity_bookstore.models.Book;
import com.cen4010.cybersecurity_bookstore.repositories.BookRepository;



@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
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

    //sprint 3: GET books by genre
    //endpoint: GET/api/books/genre/{genre}
    @GetMapping("/genre/{genre}")
    public ResponseEntity<Map<String, Object>> getBooksByGenre(@PathVariable String genre){
        Map<String,Object> response = new HashMap<>();

        try {
            //validate that genre isn't empty
            if(genre == null || genre.trim().isEmpty()){
                response.put("success", false);
                response.put("error", "Genre parameter is required");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

        //get books from the database
        List<Book> books = bookRepository.findByGenreOrderByTitleAsc(genre);

        //check if any books are found
        if(books.isEmpty()){
            response.put("success", false);
            response.put("message", "No books found for: " + genre);
            response.put("data", books);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        //return success and books
        response.put("success", true);
        response.put("count", books.size());
        response.put("genre", genre);
        response.put("data", books);
        return ResponseEntity.ok(response);

        } catch (Exception e) {
            //handle errors
            response.put("success", false);
            response.put("error", "Internal server error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //sprint 3: GET top 10 bestsellers
    //endpoint GET /api/books/top-sellers
    @GetMapping("/top-sellers")
    public ResponseEntity<Map<String, Object>> getTopSellers() {
        Map<String, Object> response = new HashMap<>();
        try {
            //get top 10 bestsellers
            List<Book> topSellers = bookRepository.findTop10ByOrderByCopiesSoldDesc();

            //check if there are any books
            if(topSellers.isEmpty()){
                response.put("success", false);
                response.put("message", "No books found in the database");
                response.put("data", topSellers);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            //return success with top sellers
            response.put("success", true);
            response.put("count", topSellers.size());
            response.put("data", topSellers);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Internal server error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    //to do in sprint 4:

    // sprint 4: GET books by rating
// endpoint: GET /api/books/rating/{rating}

    @GetMapping("/rating/{rating}")
    public ResponseEntity<Map<String, Object>> getBooksByRating(@PathVariable double rating) {
        Map<String, Object> response = new HashMap<>();

        try {
            // validate rating
            if (rating < 0 || rating > 5) {
                response.put("success", false);
                response.put("error", "Rating must be between 0 and 5");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

            // get books from database
            List<Book> books = bookRepository.findByAverageRatingGreaterThanEqualOrderByAverageRatingDesc(BigDecimal.valueOf(rating));

            // check if any books found
            if (books.isEmpty()) {
                response.put("success", false);
                response.put("message", "No books found with rating >= " + rating);
                response.put("data", books);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // return success
            response.put("success", true);
            response.put("count", books.size());
            response.put("minRating", rating);
            response.put("data", books);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("error", "Internal server error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    //add PUT endpoint for discount
    
}
