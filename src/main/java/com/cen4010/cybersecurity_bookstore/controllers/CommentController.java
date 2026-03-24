package com.cen4010.cybersecurity_bookstore.controllers;

import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

import com.cen4010.cybersecurity_bookstore.models.Comment;
import com.cen4010.cybersecurity_bookstore.repositories.CommentRepository;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentRepository commentRepository;

    public CommentController(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    // POST: Create a new comment
    @PostMapping
    public Comment createComment(@RequestBody Comment comment) {

        comment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

    // GET: Retrieve comments for a specific book
    @GetMapping("/book/{bookId}")
    public List<Comment> getCommentsByBook(@PathVariable Long bookId) {

        return commentRepository.findByBookId(bookId);
    }
}
