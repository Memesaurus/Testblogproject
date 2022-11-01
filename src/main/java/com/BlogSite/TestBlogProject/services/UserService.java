package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.Dto.UserDto;
import com.BlogSite.TestBlogProject.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    User getUser(Long id);
    User getUserByUsername(String username);
    ResponseEntity<?> addUser(UserDto UserDto);
}
