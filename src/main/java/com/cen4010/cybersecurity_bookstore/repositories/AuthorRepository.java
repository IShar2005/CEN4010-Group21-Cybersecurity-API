package com.cen4010.cybersecurity_bookstore.repositories;

import com.cen4010.cybersecurity_bookstore.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository //Sprint 5 - Michael Scott
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    
}
