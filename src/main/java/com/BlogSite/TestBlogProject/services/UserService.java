package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.model.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User getUser(Long id);
}
