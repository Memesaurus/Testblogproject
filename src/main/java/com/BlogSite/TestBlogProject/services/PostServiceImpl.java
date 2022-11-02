package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.Dto.PostDto;
import com.BlogSite.TestBlogProject.models.ErrorCode;
import com.BlogSite.TestBlogProject.models.Post;
import com.BlogSite.TestBlogProject.models.Result;
import com.BlogSite.TestBlogProject.models.User;
import com.BlogSite.TestBlogProject.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;

    @Override
    public List<Post> getPostsByUsername(String username) {
        User user = userService.getUserByUsername(username).getData();
        return postRepository.findAllByUser_id(user.getId());
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public ResponseEntity<?> addPost(PostDto postDto) {
        Result<User> userResult = userService.getUserByUsername(postDto.getUsername());
        if (userResult.getError() == ErrorCode.USER_NOT_FOUND) {
            return ResponseEntity.badRequest().body(userResult.getError());
        }
        Post post = new Post();
        post.setBody(postDto.getBody());
        post.setUser(userResult.getData());
        post = postRepository.save(post);
        return ResponseEntity.ok().body(post);
    }
}
