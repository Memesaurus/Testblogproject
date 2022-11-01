package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.Dto.PostDto;
import com.BlogSite.TestBlogProject.models.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {
    List<Post> getPostsByUsername(String username);
    List<Post> getAllPosts();
    ResponseEntity<?> addPost(PostDto postDto);
}
