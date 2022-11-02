package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.Dto.UserDto;
import com.BlogSite.TestBlogProject.models.Result;
import com.BlogSite.TestBlogProject.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    Result<User> getUser(Long id);

    Result<User> getUserByUsername(String username);

    Result<ResponseEntity<?>> addUser(UserDto UserDto);
}
