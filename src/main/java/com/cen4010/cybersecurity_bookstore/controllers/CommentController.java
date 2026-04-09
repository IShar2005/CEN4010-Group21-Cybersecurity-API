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

    // PUT: Update comments for a specific book
    @PutMapping("/{id}")
    public Comment updateComment(@PathVariable Long id, @RequestBody Comment updatedComment) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id " + id));

        existingComment.setContent(updatedComment.getContent());
        existingComment.toString(updatedComment.getRating());

        // Optional: update timestamp when edited
        existingComment.setCreatedAt(LocalDateTime.now());

        return commentRepository.save(existingComment);
    }

    // GET: Retrieve comments for a specific book
    @GetMapping("/book/{bookId}")
    public List<Comment> getCommentsByBook(@PathVariable Long bookId) {

        return commentRepository.findByBookId(bookId);
    }
    // DELETE: Delete comments on specific book
    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found with id " + id));

        commentRepository.delete(comment);

        return "Comment deleted successfully!";
    }
}
