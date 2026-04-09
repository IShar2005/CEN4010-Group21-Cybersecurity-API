package com.cen4010.cybersecurity_bookstore.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.cen4010.cybersecurity_bookstore.models.Rating;
import com.cen4010.cybersecurity_bookstore.repositories.RatingRepository;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final RatingRepository ratingRepository;

    public RatingController(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    // POST a rating
    @PostMapping
    public Rating createRating(@RequestBody Rating rating) {
        return ratingRepository.save(rating);
    }

    // GET ratings for a book
    @GetMapping("/book/{bookId}")
    public List<Rating> getRatingsByBook(@PathVariable Long bookId) {
        return ratingRepository.findByBookId(bookId);
    }

    // PUT ratings for a book
    @PutMapping("/{id}")
    public Rating updateRating(@PathVariable Long id, @RequestBody Rating updatedRating) {
        Rating existingRating = ratingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rating not found with id " + id));

        existingRating.setScore(updatedRating.getScore());
        existingRating.setBookId(updatedRating.getBookId()); // optional if you allow changing book

        return ratingRepository.save(existingRating);
    }

    // DELETE ratings for book
    @DeleteMapping("/{id}")
    public String deleteRating(@PathVariable Long id) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rating not found with id " + id));

        ratingRepository.delete(rating);

        return "Rating deleted successfully!";
    }

}