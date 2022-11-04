package com.BlogSite.TestBlogProject.controllers;

import com.BlogSite.TestBlogProject.dto.PostDto;
import com.BlogSite.TestBlogProject.models.Post;
import com.BlogSite.TestBlogProject.models.Result;
import com.BlogSite.TestBlogProject.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/{username}")
    public Result<List<Post>> getPostsByUsername(@PathVariable String username) {
        return postService.getPostsByUsername(username);
    }

    @GetMapping()
    public List<Post> getAll() {
        return postService.getAllPosts();
    }

    @PostMapping
    public Result<Post> postPost(@RequestBody PostDto postDto) {
        return postService.addPost(postDto);
    }
}
