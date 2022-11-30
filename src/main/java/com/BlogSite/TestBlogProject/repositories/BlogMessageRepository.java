package com.BlogSite.TestBlogProject.repositories;

import com.BlogSite.TestBlogProject.models.BlogMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogMessageRepository extends JpaRepository<BlogMessage, Long> {
}