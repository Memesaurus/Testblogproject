package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.dto.PostDto;
import com.BlogSite.TestBlogProject.models.ErrorCode;
import com.BlogSite.TestBlogProject.models.Post;
import com.BlogSite.TestBlogProject.models.Result;
import com.BlogSite.TestBlogProject.models.User;
import com.BlogSite.TestBlogProject.repositories.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @Mock
    private UserServiceImpl userService;
    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private PostServiceImpl postService;

    @Test
    void getPostsByUsername_ShouldGetPostsByUsername() {
        String username = "Test";
        User user = new User(null,
                username,
                null);
        Post post = new Post(null,
                "Test",
                user);
        List<Post> expectedList = List.of(post);

        doReturn(Optional.of(expectedList))
                .when(postRepository).findByUserUsername(username);
        List<Post> actualList = postService.getPostsByUsername(username).getData();

        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void getPostsByUsername_ShouldReturnErrorCode() {
        ErrorCode expectedError = ErrorCode.USER_NOT_FOUND;
        String username = "Test";

        doReturn(Optional.empty())
                .when(postRepository).findByUserUsername(username);
        ErrorCode actualError = postService.getPostsByUsername(username).getError();

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
        Result<User> mockResult = new Result<>();
        mockResult.setData(expectedUser);
        PostDto postDto = new PostDto();
        postDto.setBody(username);
        postDto.setUsername(username);

        doReturn(mockResult)
                .when(userService).getUserByUsername(username);
        doReturn(expectedPost)
                .when(postRepository).save(mockPost);
        Post actualPost = postService.addPost(postDto).getData();

        Assertions.assertEquals(expectedPost, actualPost);
    }

    @Test
    void addPost_ShouldReturnErrorCode() {
        ErrorCode expectedResponse = ErrorCode.USER_NOT_FOUND;
        Result<User> mockResult = new Result<>();
        mockResult.setError(expectedResponse);
        PostDto postDto = new PostDto();
        postDto.setUsername(null);
        postDto.setBody(null);

        doReturn(mockResult)
                .when(userService).getUserByUsername(null);
        ErrorCode actualResponse = postService.addPost(postDto).getError();

        Assertions.assertEquals(expectedResponse, actualResponse);
    }
}