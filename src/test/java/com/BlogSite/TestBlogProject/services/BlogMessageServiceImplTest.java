package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.annotations.WithMockMyUserDetails;
import com.BlogSite.TestBlogProject.dto.BlogMessageDto;
import com.BlogSite.TestBlogProject.mapper.BlogMessageMapper;
import com.BlogSite.TestBlogProject.models.*;
import com.BlogSite.TestBlogProject.repositories.PostRepository;
import com.BlogSite.TestBlogProject.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration
class BlogMessageServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;
    @Mock
    private BlogMessageMapper postMapper;
    @InjectMocks
    private BlogMessageServiceImpl postService;

    @Test
    void getActivePostsByUserUsername_ShouldGetActivePostsByUsername() {
        String username = "Test";
        User user = new User(null,
                username,
                username,
                username,
                true,
                null);
        Post post = new Post(null,
                "Test",
                user);
        List<Post> expectedList = List.of(post);

        doReturn(Optional.of(expectedList))
                .when(postRepository).findPostsAndActiveComments(username, true);
        List<Post> actualList = postService.getActivePostsByUserUsername(username).getData();

        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void getLikeCountByPostId_ShouldReturnCount() {
        Long expectedCount = 100L;
        Post post = new Post(1L,
                "Test",
                new User());
        post.setLikeCount(100L);

        doReturn(Optional.of(post))
                .when(postRepository).findById(1L);
        Long actualCount = postService.getLikeCountByBlogMessageId(1L).getData();

        Assertions.assertEquals(expectedCount, actualCount);
    }

    @Test
    void getPostsByUsername_ShouldReturnErrorCode() {
        ErrorCode expectedError = ErrorCode.NOT_FOUND;
        String username = "Test";

        ErrorCode actualError = postService.getActivePostsByUserUsername(username).getError();

        Assertions.assertEquals(expectedError, actualError);
    }

    @Test
    void getAllPosts_ShouldGetAllPosts() {
        postService.getAllPosts();

        verify(postRepository).findAll();
    }

    @Test
    void addPost_ShouldSavePost() {
        String username = "Test";
        User expectedUser = new User();
        Post mockPost = new Post(null,
                username,
                expectedUser);
        Post expectedPost = new Post(1L,
                username,
                expectedUser);
        BlogMessageDto blogMessageDto = new BlogMessageDto();
        blogMessageDto.setBody(username);
        blogMessageDto.setUsername(username);

        doReturn(Optional.of(expectedUser))
                .when(userRepository).findByUsername(username);
        doReturn(mockPost)
                .when(postMapper).blogMessageDtoToPost(blogMessageDto, expectedUser);
        doReturn(expectedPost)
                .when(postRepository).save(mockPost);
        Post actualPost = postService.addPost(blogMessageDto).getData();

        Assertions.assertEquals(expectedPost, actualPost);
    }

    @Test
    @WithMockMyUserDetails
    void changeUserBlogMessageLikeState_ShouldChangeLikeStates() {
        User user = new User(1L,
                "Test",
                "Test",
                "Test",
                true,
                null);
        Post likedPost = new Post(1L, "IsLiked", user);
        likedPost.addLike(user);
        Post expectedLikedPost = new Post(1L, "IsLiked", user);
        Post notLikedPost = new Post(2L, "IsNotLiked", user);
        Post expectedNotLikedPost = new Post(2L, "IsNotLiked", user);
        expectedNotLikedPost.addLike(user);

        doReturn(Optional.of(user))
                .when(userRepository).findById(1L);
        doReturn(Optional.of(likedPost))
                .when(postRepository).findById(1L);
        doReturn(expectedLikedPost)
                .when(postRepository).save(likedPost);
        BlogMessage actualNotLikedPost = postService.changeUserBlogMessageLikeState(1L).getData();
        doReturn(Optional.of(notLikedPost))
                .when(postRepository).findById(2L);
        doReturn(expectedNotLikedPost)
                .when(postRepository).save(notLikedPost);
        BlogMessage actualLikedPost = postService.changeUserBlogMessageLikeState(2L).getData();

        Assertions.assertEquals(likedPost, actualNotLikedPost);
        Assertions.assertEquals(notLikedPost, actualLikedPost);
    }

    @Test
    @WithMockUser
    void getCurrentUser_ShouldReturnUnexpectedPrincipal() {
        ErrorCode expectedError = ErrorCode.UNEXPECTED_PRINCIPAL;

        ErrorCode actualError = postService.getCurrentUser().getError();

        Assertions.assertEquals(expectedError, actualError);
    }

    @Test
    @WithMockMyUserDetails
    void changeUserPostLikeState_ShouldReturnNotFound() {
        ErrorCode expectedError = ErrorCode.NOT_FOUND;
        User user = new User(1L,
                "Test",
                "Test",
                "Test",
                true,
                new Role());

        doReturn(Optional.of(user))
                .when(userRepository).findById(1L);
        ErrorCode actualError = postService.changeUserBlogMessageLikeState(1L).getError();

        Assertions.assertEquals(expectedError, actualError);
    }

    @Test
    @WithMockMyUserDetails
    void hideUserPost_ShouldUpdateUserPost() {
        User user = new User(1L,
                "Test",
                "Test",
                "Test",
                true,
                null);
        Post post = new Post(1L,
                "Test",
                user);
        Post expectedPost = new Post(1L,
                "Test",
                user);
        expectedPost.setIsActive(false);

        doReturn(Optional.of(post))
                .when(postRepository).findById(1L);
        doReturn(expectedPost)
                .when(postRepository).save(post);
        BlogMessage actualPost = postService.hideUserBlogMessage(1L).getData();

        Assertions.assertEquals(expectedPost, actualPost);
    }

    @Test
    @WithMockMyUserDetails
    void hideUserPost_ShouldReturnUnauthorised() {
        Post post = new Post(1L,
                "Test",
                new User());
        ErrorCode expectedError = ErrorCode.UNAUTHORISED;

        doReturn(Optional.of(post))
                .when(postRepository).findById(1L);
        ErrorCode actualError = postService.hideUserBlogMessage(1L).getError();

        Assertions.assertEquals(expectedError, actualError);
    }

    @Test
    void addPost_ShouldReturnErrorCode() {
        ErrorCode expectedResponse = ErrorCode.NOT_FOUND;
        BlogMessageDto blogMessageDto = new BlogMessageDto();
        blogMessageDto.setUsername(null);
        blogMessageDto.setBody("123");

        doReturn(Optional.empty())
                .when(userRepository).findByUsername(null);
        ErrorCode actualResponse = postService.addPost(blogMessageDto).getError();

        Assertions.assertEquals(expectedResponse, actualResponse);
    }
}