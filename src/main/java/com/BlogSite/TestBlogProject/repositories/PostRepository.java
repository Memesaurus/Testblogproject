package com.BlogSite.TestBlogProject.repositories;

import com.BlogSite.TestBlogProject.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<List<Post>> findByUserUsername(@Param("username") String username);

    @Query("SELECT DISTINCT p FROM Post p " +
            "JOIN p.user u " +
            "LEFT JOIN FETCH p.comments c " +
            "WHERE u.username = :username " +
            "AND p.isActive = :isActive")
    Optional<List<Post>> findPostsAndActiveComments(
            @Param("username") String username,
            @Param("isActive") Boolean isActive);

    List<Post> findByUserId(Long id);
}
