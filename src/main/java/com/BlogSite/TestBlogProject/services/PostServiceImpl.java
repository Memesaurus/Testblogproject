package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.dto.PostDto;
import com.BlogSite.TestBlogProject.mapper.PostMapper;
import com.BlogSite.TestBlogProject.models.*;
import com.BlogSite.TestBlogProject.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PostMapper postMapper;

    public Result<User> getCurrentUser() {
        Result<User> result = new Result<>();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof MyUserDetails)) {
            result.setError(ErrorCode.UNEXPECTED_PRINCIPAL);
            return result;
        }
        User user = ((MyUserDetails) principal).getUser();
        result.setData(user);
        return result;
    }

    @Override
    public Result<List<Post>> getActivePostsByUserUsername(String username) {
        Result<List<Post>> result = new Result<>();
        postRepository.findByUserUsernameAndIsActive(username, true).ifPresentOrElse(
                result::setData,
                () -> result.setError(ErrorCode.NOT_FOUND));
        return result;
    }

    @Override
    public Result<Post> changeUserPostLikeState(Long postId) {
        Result<Post> result = new Result<>();
        Result<User> userResult = getCurrentUser();
        if (userResult.getError() != null) {
            result.setError(userResult.getError());
            return result;
        }
        Long userId = userResult.getData().getId();
        Result<User> currentUserResult = userService.getUser(userId);
        if (currentUserResult.getError() != null) {
            result.setError(currentUserResult.getError());
            return result;
        }
        User currentUser = currentUserResult.getData();
        postRepository.findById(postId).ifPresentOrElse(
                post -> {
                    if (post.getUserLikes().contains(currentUser)) {
                        post.removeLike(currentUser);
                    } else {
                        post.addLike(currentUser);
                    }
                    Post savedPost = postRepository.save(post);
                    result.setData(savedPost);
                },
                () -> result.setError(ErrorCode.NOT_FOUND)
        );
        return result;
    }

    @Override
    public Result<Long> getLikeCountByPostId(Long postId) {
        Result<Long> result = new Result<>();
        postRepository.findById(postId).ifPresentOrElse(
                post -> result.setData(post.getLikeCount()),
                () -> result.setError(ErrorCode.NOT_FOUND)
        );
        return result;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Result<Post> addPost(PostDto postDto) {
        Result<Post> result = new Result<>();
        if (postDto.getBody() == null) {
            result.setError(ErrorCode.EMPTY_BODY);
        }
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
    @Transactional
    public Result<Post> hideUserPost(Long postId) {
        Result<Post> result = new Result<>();
        Result<User> userResult = getCurrentUser();
        if (userResult.getError() != null) {
            result.setError(userResult.getError());
            return result;
        }
        User currentUser = userResult.getData();
        postRepository.findById(postId).ifPresentOrElse(
                post -> {
                    if (currentUser.getId().equals(post.getUser().getId())) {
                        post.setIsActive(false);
                        Post savedPost = postRepository.save(post);
                        result.setData(savedPost);
                    } else {
                        result.setError(ErrorCode.UNAUTHORISED);
                    }
                },
                () -> result.setError(ErrorCode.NOT_FOUND));
        return result;
    }
}
