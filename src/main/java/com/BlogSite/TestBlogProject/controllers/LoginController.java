package com.BlogSite.TestBlogProject.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @GetMapping("?success")
    public void loginResult() {
        // Placeholder endpoint to communicate through frontend server
    }

    @PostMapping
    public void loginPost() {
        // PH
    }

}
