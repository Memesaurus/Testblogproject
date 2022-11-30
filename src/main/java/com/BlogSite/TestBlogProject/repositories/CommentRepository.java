package com.BlogSite.TestBlogProject.repositories;

import com.BlogSite.TestBlogProject.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}