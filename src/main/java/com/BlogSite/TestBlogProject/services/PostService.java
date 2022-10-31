package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.DTO.PostDto;
import com.BlogSite.TestBlogProject.models.Post;
import com.BlogSite.TestBlogProject.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {
    List<Post> getPostsByUsername(String username);
    List<Post> getAllPosts();
    ResponseEntity<?> postPost(PostDto postDto);
}
