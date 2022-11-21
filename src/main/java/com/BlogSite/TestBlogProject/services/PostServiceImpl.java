package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.dto.PostDto;
import com.BlogSite.TestBlogProject.mapper.PostMapper;
import com.BlogSite.TestBlogProject.models.ErrorCode;
import com.BlogSite.TestBlogProject.models.Post;
import com.BlogSite.TestBlogProject.models.Result;
import com.BlogSite.TestBlogProject.models.User;
import com.BlogSite.TestBlogProject.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PostMapper postMapper;

    @Override
    public Result<List<Post>> getPostsByUsername(String username) {
        Result<List<Post>> result = new Result<>();
        postRepository.findByUserUsername(username).ifPresentOrElse(
                result::setData,
                () -> result.setError(ErrorCode.NOT_FOUND));
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
        if (userResult.getError() == ErrorCode.NOT_FOUND) {
            result.setError(userResult.getError());
        } else {
            Post post = postMapper.postDtoToPost(postDto, userResult.getData());
            post = postRepository.save(post);
            result.setData(post);
        }
        return result;
    }

    @Override
    public void deleteAllPosts() {
        postRepository.deleteAll();
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    public Result<Post> deleteUserPost(Long postId) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Object principal = authentication.getPrincipal();
        Result<Post> result = new Result<>();
        postRepository.findById(postId).ifPresentOrElse(
                post -> {
                    if (post.getUser().equals(principal)) {
                        postRepository.deleteById(postId);
                        result.setData(new Post());
                    } else {
                        result.setError(ErrorCode.UNAUTHORISED);
                    }
                },
                () -> result.setError(ErrorCode.NOT_FOUND));
        return result;
    }
}
