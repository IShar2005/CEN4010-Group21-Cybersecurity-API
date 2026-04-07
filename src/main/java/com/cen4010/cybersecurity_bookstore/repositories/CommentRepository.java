package com.cen4010.cybersecurity_bookstore.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<com.cen4010.cybersecurity_bookstore.models.Comment, Long> {

    List<com.cen4010.cybersecurity_bookstore.models.Comment> findByBookId(Long bookId);

}