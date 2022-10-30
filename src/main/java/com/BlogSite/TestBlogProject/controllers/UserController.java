package com.BlogSite.TestBlogProject.controllers;

import com.BlogSite.TestBlogProject.model.User;
import com.BlogSite.TestBlogProject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String HelloWorld() {
        return "Hello, World!";
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/user/{id}")
    public User getOneUser(@PathVariable Long id) {
        return userService.getUser(id);
    }
}
