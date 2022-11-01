package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.Dto.PostDto;
import com.BlogSite.TestBlogProject.models.Post;
import com.BlogSite.TestBlogProject.models.User;
import com.BlogSite.TestBlogProject.repositories.PostRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {
    @Mock
    private UserServiceImpl userService;
    @Mock
    private PostRepository postRepository;
    @InjectMocks
    private PostServiceImpl test;

    @Test
    void getPostsByUsername_ShouldGetPostsByUsername() {
        String username = "TestUser";
        String email = "TestUser@test.com";
        User user = new User(1L,
                username,
                email
        );

        test.getPostsByUsername(username);

        ArgumentCaptor<String> stringArgumentCaptor =
                ArgumentCaptor.forClass(String.class);
        verify(postRepository)
                .findAllByUser_id(stringArgumentCaptor.capture());
        String capturedUsername = stringArgumentCaptor.getValue();
        assertThat(capturedUsername).isEqualTo(username);
    }

    @Test
    void getAllPosts_ShouldGetAllPosts() {
        test.getAllPosts();

        verify(postRepository).findAll();
    }

    @Test
    void addPost_ShouldSavePost() {
        String username = "Test";
        String email = "TestUser@test.com";
        String body = "Test";
        User expectedUser = new User(1L,
                username,
                email);
        Post expectedPost = new Post(null,
                body,
                expectedUser);
        PostDto postDto = new PostDto();
        postDto.setBody(body);
        postDto.setUsername(username);

        doReturn(expectedUser).when(userService).getUserByUsername(username);
        test.addPost(postDto);

        ArgumentCaptor<Post> postArgumentCaptor =
                ArgumentCaptor.forClass(Post.class);
        verify(postRepository).save(postArgumentCaptor.capture());
        Post capturedPost = postArgumentCaptor.getValue();
        assertThat(capturedPost).usingRecursiveComparison().isEqualTo(expectedPost);
    }

    @Test
    void addPost_ShouldReturnResponseEntityBADREQUEST() {
        PostDto postDto = new PostDto();
        postDto.setUsername(null);
        postDto.setBody(null);
        ResponseEntity<?> expectedResponse =
                ResponseEntity.badRequest().body("USERNOTFOUND");

        ResponseEntity<?> response = test.addPost(postDto);

        assertThat(response).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}