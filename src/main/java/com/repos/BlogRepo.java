package com.repos;

import com.entities.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlogRepo extends JpaRepository<Blog, Long> {

    // Find a blog by title
    Optional<Blog> findByTitle(String title);

    // Find all blogs for a specific user
    @Query("SELECT b FROM Blog b JOIN b.users u WHERE u.id = :userId")
    List<Blog> findAllByUserId(Long userId);

    // Find blogs with a specific keyword in the title or content
    @Query("SELECT b FROM Blog b WHERE b.title LIKE %:keyword% OR b.content LIKE %:keyword%")
    List<Blog> searchBlogsByKeyword(String keyword);

    // Get blogs with a specific thumbnail URL
    @Query("SELECT b FROM Blog b WHERE b.thumbnailUrl = :thumbnailUrl")
    List<Blog> findBlogsByThumbnailUrl(String thumbnailUrl);
}
