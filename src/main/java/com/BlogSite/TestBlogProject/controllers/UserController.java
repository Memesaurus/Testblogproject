package com.BlogSite.TestBlogProject.controllers;

import com.BlogSite.TestBlogProject.Dto.UserDto;
import com.BlogSite.TestBlogProject.models.User;
import com.BlogSite.TestBlogProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping()
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getOneUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PostMapping
    public ResponseEntity<?> postUser(@RequestBody UserDto UserDto) {
        System.out.println(UserDto.toString());
        return userService.addUser(UserDto);
    }
}
