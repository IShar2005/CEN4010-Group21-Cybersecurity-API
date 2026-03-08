package com.cen4010.cybersecurity_bookstore.repositories;

import com.cen4010.cybersecurity_bookstore.models.Book;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    // JpaRepository gives us findAll() and findById() automatically
    // We will add more custom queries in Sprint 3
    Optional<Book> findByIsbn(String isbn); // Sprint 3 - Michael Scott
}


