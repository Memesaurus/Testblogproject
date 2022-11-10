package com.BlogSite.TestBlogProject.integration;

import com.BlogSite.TestBlogProject.dto.UserDto;
import com.BlogSite.TestBlogProject.models.User;
import com.BlogSite.TestBlogProject.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addUser_ShouldAddUserToDataBase() throws Exception {
        Long id = 1L;
        String name = "Test";
        UserDto userDto = new UserDto();
        userDto.setEmail(name);
        userDto.setUsername(name);
        User expectedUser = new User(id, name, name);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").isEmpty())
                .andDo(print());

        List<User> userList = userRepository.findAll();
        Assertions.assertTrue(userList.contains(expectedUser));
    }


    @Test
    void getUser_ShouldReturnUserByUserId() throws Exception {
        Long id = 1L;
        String name = "Test";
        User user = new User(id, name, name);

        mockMvc.perform(get("/api/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value(user))
                .andDo(print());
    }


}
