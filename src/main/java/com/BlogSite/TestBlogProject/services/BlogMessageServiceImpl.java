package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.dto.BlogMessageDto;
import com.BlogSite.TestBlogProject.mapper.BlogMessageMapper;
import com.BlogSite.TestBlogProject.models.*;
import com.BlogSite.TestBlogProject.repositories.BlogMessageRepository;
import com.BlogSite.TestBlogProject.repositories.CommentRepository;
import com.BlogSite.TestBlogProject.repositories.PostRepository;
import com.BlogSite.TestBlogProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogMessageServiceImpl implements BlogMessageService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BlogMessageRepository blogMessageRepository;
    @Autowired
    private BlogMessageMapper blogMessageMapper;
    @Autowired
    private CommentRepository commentRepository;

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

    private List<Post> filterDeletedComments(List<Post> list) {
        list.forEach(post ->
            post.setComments(post.getComments().stream().filter(BlogMessage::getIsActive).collect(Collectors.toSet()))
        );
        return list;
    }

    @Override
    public Result<List<Post>> getActivePostsByUserUsername(String username) {
        Result<List<Post>> result = new Result<>();
        postRepository.findPostsAndActiveComments(username, true).ifPresentOrElse(
                posts -> result.setData(filterDeletedComments(posts)),
                () -> result.setError(ErrorCode.NOT_FOUND));
        return result;
    }

    @Override
    public Result<BlogMessage> changeUserBlogMessageLikeState(Long postId) {
        Result<BlogMessage> result = new Result<>();
        Result<User> userResult = getCurrentUser();
        if (userResult.getError() != null) {
            result.setError(userResult.getError());
            return result;
        }
        Long userId = userResult.getData().getId();
        userRepository.findById(userId).ifPresentOrElse(
                user -> blogMessageRepository.findById(postId).ifPresentOrElse(
                        blogMessage -> {
                            if (blogMessage.getUserLikes().contains(user)) {
                                blogMessage.removeLike(user);
                            } else {
                                blogMessage.addLike(user);
                            }
                            BlogMessage savedBlogMessage = blogMessageRepository.save(blogMessage);
                            result.setData(savedBlogMessage);
                        },
                        () -> result.setError(ErrorCode.NOT_FOUND)
                ),
                () -> result.setError(ErrorCode.NOT_FOUND)
        );
        return result;
    }

    @Override
    public Result<Long> getLikeCountByBlogMessageId(Long postId) {
        Result<Long> result = new Result<>();
        blogMessageRepository.findById(postId).ifPresentOrElse(
                blogMessage -> result.setData(blogMessage.getLikeCount()),
                () -> result.setError(ErrorCode.NOT_FOUND)
        );
        return result;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Result<Post> addPost(BlogMessageDto blogMessageDto) {
        Result<Post> result = new Result<>();
        Result<User> currentUserResult = getCurrentUser();
        if (currentUserResult.getError() != null) {
            result.setError(currentUserResult.getError());
            return result;
        }
        if (blogMessageDto.getBody() == null) {
            result.setError(ErrorCode.EMPTY_BODY);
            return result;
        }
        User currentUser = currentUserResult.getData();
        userRepository.findByUsername(blogMessageDto.getUsername()).ifPresentOrElse(
                user -> {
                    if (user.getId().equals(currentUser.getId())) {
                        Post post = blogMessageMapper.blogMessageDtoToPost(blogMessageDto, user);
                        Post savedPost = postRepository.save(post);
                        result.setData(savedPost);
                    } else {
                        result.setError(ErrorCode.UNAUTHORISED);
                    }
                },
                () -> result.setError(ErrorCode.NOT_FOUND)
        );
        return result;
    }

    @Override
    public Result<Comment> addCommentToPost(BlogMessageDto blogMessageDto, Long parentId) {
        Result<Comment> result = new Result<>();
        if (blogMessageDto.getBody() == null) {
            result.setError(ErrorCode.EMPTY_BODY);
            return result;
        }
        postRepository.findById(parentId).ifPresentOrElse(
                post -> userRepository.findByUsername(blogMessageDto.getUsername()).ifPresentOrElse(
                        user -> {
                            Comment comment = blogMessageMapper.blogMessageDtoToComment(blogMessageDto, user, parentId);
                            Comment savedComment = commentRepository.save(comment);
                            result.setData(savedComment);
                        },
                        () -> result.setError(ErrorCode.NOT_FOUND)
                ),
                () -> result.setError(ErrorCode.NOT_FOUND)
        );
        return result;
    }

    @Override
    public void deleteAllPosts() {
        postRepository.deleteAll();
    }

    @Override
    public void deleteBlogMessage(Long postId) {
        postRepository.deleteById(postId);
    }

    @Override
    @Transactional
    public Result<BlogMessage> hideUserBlogMessage(Long postId) {
        Result<BlogMessage> result = new Result<>();
        Result<User> userResult = getCurrentUser();
        if (userResult.getError() != null) {
            result.setError(userResult.getError());
            return result;
        }
        User currentUser = userResult.getData();
        blogMessageRepository.findById(postId).ifPresentOrElse(
                blogMessage -> {
                    if (currentUser.getId().equals(blogMessage.getUser().getId())) {
                        blogMessage.setIsActive(false);
                        BlogMessage savedBlogMessage = blogMessageRepository.save(blogMessage);
                        result.setData(savedBlogMessage);
                    } else {
                        result.setError(ErrorCode.UNAUTHORISED);
                    }
                },
                () -> result.setError(ErrorCode.NOT_FOUND));
        return result;
    }
}
