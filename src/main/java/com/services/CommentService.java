package com.services;

import com.entities.Comment;
import com.repos.CommentRepo;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepository;

    @Transactional
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByBlog(Long blogId) {
        return commentRepository.findByBlogId(blogId);
    }

    public List<Comment> getCommentsByUser(Long userId) {
        return commentRepository.findByUserId(userId);
    }

    public List<Comment> searchCommentsByKeyword(String keyword) {
        return commentRepository.findCommentsByKeyword(keyword);
    }

    public List<Comment> getCommentsByBlogAndUser(Long blogId, Long userId) {
        return commentRepository.findByBlogAndUser(blogId, userId);
    }
}
