package com.BlogSite.TestBlogProject.repositories;

import com.BlogSite.TestBlogProject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}