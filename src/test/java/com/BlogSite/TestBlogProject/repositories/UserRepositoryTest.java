package com.BlogSite.TestBlogProject.repositories;

import com.BlogSite.TestBlogProject.models.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindUserByGivenUsername() {
        String username = "Test";
        String email = "Test@test.com";
        User expected = new User(1L,
                username,
                email
        );
        userRepository.save(expected);

        User result = userRepository.findByUsername(username).orElse(null);

        assertThat(result).usingRecursiveComparison().isEqualTo(expected);
    }
}