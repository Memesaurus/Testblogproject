package com.BlogSite.TestBlogProject.controllers;

import com.BlogSite.TestBlogProject.dto.AuthRequestDto;
import com.BlogSite.TestBlogProject.dto.UserDto;
import com.BlogSite.TestBlogProject.models.Result;
import com.BlogSite.TestBlogProject.models.User;
import com.BlogSite.TestBlogProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/current")
    public String getCurrentUserUsername() { return userService.getAuthenticatedUserUsername(); }

    @GetMapping("/{id}")
    public Result<User> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    public Result<User> postUser(@RequestBody AuthRequestDto authRequestDto) {
        return userService.addUser(authRequestDto);
    }
}
