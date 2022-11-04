package com.BlogSite.TestBlogProject.repositories;

import com.BlogSite.TestBlogProject.models.Post;
import com.BlogSite.TestBlogProject.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldFindAllUserPostsByGivenUsername() {
        Long userId = 1L;
        String username = "TestUser";
        String email = "TestUser@test.com";
        User user = new User(userId,
                username,
                email
        );
        Post postOne = new Post(1L,
                "Test1",
                user
        );
        Post postTwo = new Post(2L,
                "Test2",
                user
        );
        userRepository.save(user);
        postRepository.save(postOne);
        postRepository.save(postTwo);

        List<Post> result = postRepository.findAllById(userId);

        List<Post> expected = new ArrayList<>();
        expected.add(postOne);
        expected.add(postTwo);
        for (int i = 0; i < result.size(); i++) {
            assertThat(result.get(i)).usingRecursiveComparison().isEqualTo(expected.get(i));
        }
    }
}