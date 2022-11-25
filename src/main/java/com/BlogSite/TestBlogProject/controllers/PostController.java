package com.BlogSite.TestBlogProject.controllers;

import com.BlogSite.TestBlogProject.dto.PostDto;
import com.BlogSite.TestBlogProject.models.Post;
import com.BlogSite.TestBlogProject.models.Result;
import com.BlogSite.TestBlogProject.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/user/{username}")
    public Result<List<Post>> getPostsByUsername(@PathVariable String username) {
        return postService.getActivePostsByUserUsername(username);
    }

    @GetMapping("/admin")
    public List<Post> getAll() {
        return postService.getAllPosts();
    }

    @PostMapping("/{id}/like")
    public Result<Post> changeUserLikeState(@PathVariable Long id) {
        return postService.changeUserPostLikeState(id);
    }

    @GetMapping("/{id}/likeCount")
    public Result<Long> getLikeCount(@PathVariable Long id) {
        return postService.getLikeCountByPostId(id);
    }

    @PostMapping
    public Result<Post> addPost(@RequestBody PostDto postDto) {
        return postService.addPost(postDto);
    }

    @DeleteMapping("/admin")
    public void deleteAll() {
        postService.deleteAllPosts();
    }

    @DeleteMapping("/admin/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

    @DeleteMapping("/user/{id}")
    public Result<Post> deleteCurrentUserPost(@PathVariable Long id) {
        return postService.hideUserPost(id);
    }
}
