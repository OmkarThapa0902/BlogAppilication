package com.controllers;

import com.entities.Blog;
import com.services.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blogs")
public class BlogController {

    @Autowired
    private BlogService blogService;

    // Create a new Blog
    @PostMapping
    public ResponseEntity<Blog> createBlog(@RequestBody Blog blog) {
        Blog newBlog = blogService.createBlog(blog);
        return new ResponseEntity<>(newBlog, HttpStatus.CREATED);
    }

    // Get all blogs
    @GetMapping
    public ResponseEntity<List<Blog>> getAllBlogs() {
        List<Blog> blogs = blogService.getAllBlogs();
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    // Get a blog by ID
    @GetMapping("/{id}")
    public ResponseEntity<Blog> getBlogById(@PathVariable Long id) {
        Blog blog = blogService.getBlogById(id);
        return new ResponseEntity<>(blog, HttpStatus.OK);
    }

    // Get blogs by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Blog>> getBlogsByUser(@PathVariable Long userId) {
        List<Blog> blogs = blogService.getBlogsByUser(userId);
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    // Search blogs by keyword
    @GetMapping("/search")
    public ResponseEntity<List<Blog>> searchBlogsByKeyword(@RequestParam String keyword) {
        List<Blog> blogs = blogService.searchBlogsByKeyword(keyword);
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    // Get blogs by thumbnail URL
    @GetMapping("/thumbnail")
    public ResponseEntity<List<Blog>> getBlogsByThumbnailUrl(@RequestParam String thumbnailUrl) {
        List<Blog> blogs = blogService.getBlogsByThumbnailUrl(thumbnailUrl);
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }
}
