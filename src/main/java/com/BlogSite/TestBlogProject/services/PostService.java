package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.dto.PostDto;
import com.BlogSite.TestBlogProject.models.Post;
import com.BlogSite.TestBlogProject.models.Result;

import java.util.List;

public interface PostService {
    Result<List<Post>> getPostsByUsername(String username);

    List<Post> getAllPosts();

    Result<Post> addPost(PostDto postDto);
}
