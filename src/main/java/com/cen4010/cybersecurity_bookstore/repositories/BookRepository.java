package com.cen4010.cybersecurity_bookstore.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cen4010.cybersecurity_bookstore.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    // JpaRepository gives us findAll() and findById() automatically
    
    //started in sprint 2, finished in 3, get books by genre
    List<Book>findByGenreOrderByTitleAsc(String genre);

    //sprint 3: get top 10 bestsellers, organized by copies sold
    List<Book> findTop10ByOrderByCopiesSoldDesc();

    List<Book> findByAverageRatingGreaterThanEqualOrderByAverageRatingDesc(BigDecimal bigDecimal);

    //To do in sprint 4 add the rating method
    //List<Book> findBYAverageRatingGreaterThanEqual(BigDecimal minRating));
}
