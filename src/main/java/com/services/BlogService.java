package com.services;

import com.entities.Blog;
import com.repos.BlogRepo;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    @Autowired
    private BlogRepo blogRepository;

    @Transactional
    public Blog createBlog(Blog blog) {
        return blogRepository.save(blog);
    }

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }

    public Blog getBlogById(Long id) {
        return blogRepository.findById(id).orElseThrow(() -> new RuntimeException("Blog not found"));
    }

    public List<Blog> getBlogsByUser(Long userId) {
        return blogRepository.findAllByUserId(userId);
    }

    public List<Blog> searchBlogsByKeyword(String keyword) {
        return blogRepository.searchBlogsByKeyword(keyword);
    }

    public List<Blog> getBlogsByThumbnailUrl(String thumbnailUrl) {
        return blogRepository.findBlogsByThumbnailUrl(thumbnailUrl);
    }
}
