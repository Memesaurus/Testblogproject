package com.BlogSite.TestBlogProject.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class SpringSecurityAuthenticationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void formLogin_ShouldReturn302ToError() throws Exception {
        mockMvc.perform(formLogin("/api/login").user("Someone"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/api/login?error"))
                .andDo(print());
    }

    @Test
    void formLogin_ShouldPerformLogout() throws Exception {
        mockMvc.perform(formLogin("/api/login").user("TestUser").password("Test"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/api/login?logout"))
                .andDo(print());
    }

    @Test
    void formLogin_ShouldReturn401() throws Exception {
        mockMvc.perform(get("/api/posts/user/someone"))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    void formLogin_ShouldPerformSuccessfulLogin() throws Exception {
        mockMvc.perform(formLogin("/api/login").user("TestUser").password("Test"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
