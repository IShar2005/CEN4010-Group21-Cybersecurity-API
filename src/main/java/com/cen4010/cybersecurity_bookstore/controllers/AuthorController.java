package com.cen4010.cybersecurity_bookstore.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.cen4010.cybersecurity_bookstore.repositories.AuthorRepository;
import com.cen4010.cybersecurity_bookstore.models.Author;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.*;



@RestController
@RequestMapping("/api/authors")
@CrossOrigin(origins = "*")
public class AuthorController {
    
    @Autowired
    private AuthorRepository AuthorRepository;

    //Gets list of authors
    @GetMapping
    public List<Author> getAllAuthors() {
        return AuthorRepository.findAll();
    }
    
    //Add new author
    @PostMapping("/create")
    public ResponseEntity<Author> createAuthor(@Valid @RequestBody Author author) {
        Author savedAuthor = AuthorRepository.save(author);
        return new ResponseEntity<>(savedAuthor, HttpStatus.CREATED);
    }

    
}
