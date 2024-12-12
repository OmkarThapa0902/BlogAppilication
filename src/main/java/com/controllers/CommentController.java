package com.controllers;

import com.entities.Comment;
import com.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Create a new comment
    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        Comment newComment = commentService.createComment(comment);
        return new ResponseEntity<>(newComment, HttpStatus.CREATED);
    }

    // Get all comments by blog ID
    @GetMapping("/blog/{blogId}")
    public ResponseEntity<List<Comment>> getCommentsByBlog(@PathVariable Long blogId) {
        List<Comment> comments = commentService.getCommentsByBlog(blogId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // Get all comments by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Comment>> getCommentsByUser(@PathVariable Long userId) {
        List<Comment> comments = commentService.getCommentsByUser(userId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // Search comments by keyword
    @GetMapping("/search")
    public ResponseEntity<List<Comment>> searchCommentsByKeyword(@RequestParam String keyword) {
        List<Comment> comments = commentService.searchCommentsByKeyword(keyword);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    // Get comments by blog ID and user ID
    @GetMapping("/blog/{blogId}/user/{userId}")
    public ResponseEntity<List<Comment>> getCommentsByBlogAndUser(@PathVariable Long blogId, @PathVariable Long userId) {
        List<Comment> comments = commentService.getCommentsByBlogAndUser(blogId, userId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
}
