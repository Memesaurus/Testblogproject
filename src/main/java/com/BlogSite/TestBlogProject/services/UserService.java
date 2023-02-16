package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.dto.AuthRequestDto;
import com.BlogSite.TestBlogProject.dto.UserDto;
import com.BlogSite.TestBlogProject.models.Result;
import com.BlogSite.TestBlogProject.models.User;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();

    String getAuthenticatedUserUsername();

    Result<User> getUser(Long id);

    Result<User> getUserByUsername(String username);

    Result<User> addUser(AuthRequestDto authRequestDto);
}
