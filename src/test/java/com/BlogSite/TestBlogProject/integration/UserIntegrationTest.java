package com.BlogSite.TestBlogProject.integration;

import com.BlogSite.TestBlogProject.dto.AuthRequestDto;
import com.BlogSite.TestBlogProject.repositories.RoleRepository;
import com.BlogSite.TestBlogProject.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Disabled
class UserIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addUser_ShouldDoPostRequest() throws Exception {
        Long id = 1L;
        String name = "Test";
        AuthRequestDto authRequestDto = new AuthRequestDto();
        authRequestDto.setEmail(name);
        authRequestDto.setPassword(name);
        authRequestDto.setUsername(name);
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").isEmpty())
                .andDo(print());

        Assertions.assertTrue(userRepository.existsById(id));
    }


    @Test
    void getUser_ShouldReturnUserByUserId() throws Exception {
        Long id = 100L;
        mockMvc.perform(get("/api/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").isEmpty())
                .andExpect(jsonPath("$.data.id").value(id))
                .andExpect(jsonPath("$.data.username").value("TestUser"))
                .andDo(print());
    }


}