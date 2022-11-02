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
        return postRepository.findAllByUser_id(username);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Result<?> addPost(PostDto postDto) {
        Result<ResponseEntity<?>> result = new Result<>();
        Result<User> userResult = userService.getUserByUsername(postDto.getUsername());
        if (userResult.getError() == ErrorCode.USER_NOT_FOUND) {
            return userResult;
        }
        Post post = new Post();
        post.setBody(postDto.getBody());
        post.setUser(userResult.getData());
        post = postRepository.save(post);
        result.setData(ResponseEntity.ok().body(post));
        return result;
    }
}
