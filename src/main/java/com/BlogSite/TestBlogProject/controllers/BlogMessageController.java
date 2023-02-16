package com.BlogSite.TestBlogProject.controllers;

import com.BlogSite.TestBlogProject.dto.BlogMessageDto;
import com.BlogSite.TestBlogProject.models.BlogMessage;
import com.BlogSite.TestBlogProject.models.Comment;
import com.BlogSite.TestBlogProject.models.Post;
import com.BlogSite.TestBlogProject.models.Result;
import com.BlogSite.TestBlogProject.services.BlogMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.GET, RequestMethod.POST})
public class BlogMessageController {
    @Autowired
    private BlogMessageService blogMessageService;

    @GetMapping("/user/{username}")
    public Result<List<Post>> getPostsByUsername(@PathVariable String username) {
        return blogMessageService.getActivePostsByUserUsername(username);
    }

    @GetMapping("/admin")
    public List<Post> getAll() {
        return blogMessageService.getAllPosts();
    }

    @PostMapping("/{id}/like")
    public Result<BlogMessage> changeUserLikeState(@PathVariable Long id) {
        return blogMessageService.changeUserBlogMessageLikeState(id);
    }

    @GetMapping("/{id}/likeCount")
    public Result<Long> getLikeCount(@PathVariable Long id) {
        return blogMessageService.getLikeCountByBlogMessageId(id);
    }

    @PostMapping
    public Result<Post> addPost(@RequestBody BlogMessageDto blogMessageDto) {
        return blogMessageService.addPost(blogMessageDto);
    }

    @PostMapping("/{id}/comments")
    public Result<Comment> postComment(@RequestBody BlogMessageDto blogMessageDto, @PathVariable Long id) {
        return blogMessageService.addCommentToPost(blogMessageDto, id);
    }

    @DeleteMapping("/admin")
    public void deleteAll() {
        blogMessageService.deleteAllPosts();
    }

    @DeleteMapping("/admin/{id}")
    public void deletePost(@PathVariable Long id) {
        blogMessageService.deleteBlogMessage(id);
    }

    @DeleteMapping("/user/{id}")
    public Result<BlogMessage> deleteCurrentUserBlogMessage(@PathVariable Long id) {
        return blogMessageService.hideUserBlogMessage(id);
    }
}
