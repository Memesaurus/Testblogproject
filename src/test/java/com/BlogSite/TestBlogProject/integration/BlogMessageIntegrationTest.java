package com.BlogSite.TestBlogProject.integration;

import com.BlogSite.TestBlogProject.annotations.WithMockMyUserDetails;
import com.BlogSite.TestBlogProject.dto.BlogMessageDto;
import com.BlogSite.TestBlogProject.models.BlogMessage;
import com.BlogSite.TestBlogProject.repositories.BlogMessageRepository;
import com.BlogSite.TestBlogProject.services.BlogMessageServiceImpl;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.control.MappingControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.constraints.AssertTrue;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Disabled
@WithMockMyUserDetails(id = 100L, username = "TestUser")
class BlogMessageIntegrationTest {
    @Autowired
    private BlogMessageServiceImpl blogMessageService;
    @Autowired
    BlogMessageRepository blogMessageRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;
    private final String USERNAME = "TestUser";

    @Test
    void getActivePostsByUserUsername_ShouldReturnAllActivePostsAndComments() throws Exception {
        mockMvc.perform(get("/api/posts/user/{username}", USERNAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").isEmpty())
                .andExpect(jsonPath("$.data[0].id").value(100L))
                .andExpect(jsonPath("$.data[0].isActive").value(true))
                .andDo(print());
    }

    @Test
    void changeUserBlogMessageLikeState_ShouldPostThenDelete() throws Exception {
        mockMvc.perform(post("/api/posts/{id}/like", 100L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").isEmpty())
                .andExpect(jsonPath("$.data.likeCount").value(1L))
                .andExpect(jsonPath("$.data.userLikes[0].username").value(USERNAME))
                .andDo(print());

        mockMvc.perform(post("/api/posts/{id}/like", 100L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").isEmpty())
                .andExpect(jsonPath("$.data.likeCount").value(0L))
                .andExpect(jsonPath("$.data.userLikes").isEmpty())
                .andDo(print());
    }

    @Test
    void addPost_ShouldDoPostRequest() throws Exception {
        BlogMessageDto blogMessageDto = new BlogMessageDto();
        blogMessageDto.setBody("Test");
        blogMessageDto.setUsername(USERNAME);

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(blogMessageDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").isEmpty())
                .andExpect(jsonPath("$.data.body").value("Test"))
                .andExpect(jsonPath("$.data.user.username").value(USERNAME))
                .andDo(print());

        Assertions.assertTrue(blogMessageRepository.existsById(1L));
    }

    @Test
    void hideUserBlogMessage_ShouldReturnHiddenBlogMessage() throws Exception {
        Long id = 100L;
        mockMvc.perform(delete("/api/posts/user/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").isEmpty())
                .andExpect(jsonPath("$.data.isActive").value(false))
                .andDo(print());

        BlogMessage blogMessage = blogMessageRepository.findById(id).get();

        Assertions.assertFalse(blogMessage.getIsActive());
    }


    @Test
    void addCommentToPost_ShouldDoPostRequest() throws Exception {
        BlogMessageDto blogMessageDto = new BlogMessageDto();
        blogMessageDto.setBody("TestComment");
        blogMessageDto.setUsername(USERNAME);

        mockMvc.perform(post("/api/posts/100/comments")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(blogMessageDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").isEmpty())
                .andExpect(jsonPath("$.data.parentId").value(100L))
                .andDo(print());
    }
}
