package com.cen4010.cybersecurity_bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.cen4010.cybersecurity_bookstore.models.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByBookId(Long bookId);

}
