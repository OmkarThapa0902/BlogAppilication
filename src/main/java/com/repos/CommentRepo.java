package com.repos;

import com.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {

    // Find comments by blog ID
    @Query("SELECT c FROM Comment c WHERE c.blog.id = :blogId")
    List<Comment> findByBlogId(Long blogId);

    // Find comments by user ID
    @Query("SELECT c FROM Comment c WHERE c.user.id = :userId")
    List<Comment> findByUserId(Long userId);

    // Find all comments containing a specific keyword
    @Query("SELECT c FROM Comment c WHERE c.content LIKE %:keyword%")
    List<Comment> findCommentsByKeyword(String keyword);

    // Find comments on a blog by a specific user
    @Query("SELECT c FROM Comment c WHERE c.blog.id = :blogId AND c.user.id = :userId")
    List<Comment> findByBlogAndUser(Long blogId, Long userId);
}
