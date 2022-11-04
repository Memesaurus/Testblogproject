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
    @Query("select p from Post p " +
            "JOIN FETCH p.user u " +
            "where u.username = :username")
    Optional<List<Post>> findByUserUsername(@Param("username") String username);

    List<Post> findByUserId(Long id);
}
