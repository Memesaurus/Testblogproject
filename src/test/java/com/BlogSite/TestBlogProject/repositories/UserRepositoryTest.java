package com.BlogSite.TestBlogProject.repositories;

import com.BlogSite.TestBlogProject.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void ShouldFindUserByGivenUsername() {
        String username = "Test";
        String email = "Test@test.com";
        User user = new User(1L,
                username,
                email
        );
        userRepository.save(user);

        User result = userRepository.findByUsername(username);

        User expected = user;
        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }
}