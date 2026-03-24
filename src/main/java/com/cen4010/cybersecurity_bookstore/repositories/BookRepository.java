package com.cen4010.cybersecurity_bookstore.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cen4010.cybersecurity_bookstore.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    // JpaRepository gives us findAll() and findById() automatically
    
    //started in sprint 2, finished in 3, get books by genre
    List<Book>findByGenreOrderByTitleAsc(String genre);

    //sprint 3: get top 10 bestsellers, organized by copies sold
    List<Book> findTop10ByOrderByCopiesSoldDesc();

    //Sprint 4: Get books by rating (greater than or equal to min)
    List<Book> findByAverageRatingGreaterThanEqualOrderByAverageRatingDesc(BigDecimal minRating);

    //Sprint 4: apply discount
    @Modifying
    @Query("UPDATE Book b SET b.price = b.price * (1 - :discountPercent / 100) WHERE b.publisher = :publisher")
    int applyDiscountByPublisher(@Param("publisher") String publisher, @Param("discountPercent") BigDecimal discountPercent);
}
