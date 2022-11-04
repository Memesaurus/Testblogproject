package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.dto.PostDto;
import com.BlogSite.TestBlogProject.models.ErrorCode;
import com.BlogSite.TestBlogProject.models.Post;
import com.BlogSite.TestBlogProject.models.Result;
import com.BlogSite.TestBlogProject.models.User;
import com.BlogSite.TestBlogProject.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;

    @Override
    public Result<List<Post>> getPostsByUsername(String username) {
        Result<List<Post>> result = new Result<>();
        User user = userService.getUserByUsername(username).getData();
        if (user == null) {
            result.setError(ErrorCode.USER_NOT_FOUND);
            return result;
        }
        result.setData(postRepository.findAllByUser_id(user.getId()));
        return result;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Result<Post> addPost(PostDto postDto) {
        Result<Post> result = new Result<>();
        Result<User> userResult = userService.getUserByUsername(postDto.getUsername());
        if (userResult.getError() == ErrorCode.USER_NOT_FOUND) {
            result.setError(userResult.getError());
            return result;
        }
        Post post = new Post();
        post.setBody(postDto.getBody());
        post.setUser(userResult.getData());
        post = postRepository.save(post);
        result.setData(post);
        return result;
    }
}
