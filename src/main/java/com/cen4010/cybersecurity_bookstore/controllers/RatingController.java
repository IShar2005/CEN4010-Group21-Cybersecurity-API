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

    // Add a rating
    @PostMapping
    public Rating createRating(@RequestBody Rating rating) {
        return ratingRepository.save(rating);
    }

    // Get ratings for a book
    @GetMapping("/book/{bookId}")
    public List<Rating> getRatingsByBook(@PathVariable Long bookId) {
        return ratingRepository.findByBookId(bookId);
    }

    // Get average rating for a book
    @GetMapping("/average/{bookId}")
    public double getAverageRating(@PathVariable Long bookId) {

        List<Rating> ratings = ratingRepository.findByBookId(bookId);

        if (ratings.isEmpty()) {
            return 0.0;
        }

        double sum = 0;

        for (Rating r : ratings) {
            sum += r.getScore();
        }

        return sum / ratings.size();
    }
}