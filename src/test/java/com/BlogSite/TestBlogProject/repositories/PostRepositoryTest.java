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
        Post postone = new Post(1L,
                "Test1",
                user
        );
        Post posttwo = new Post(2L,
                "Test2",
                user
        );
        userRepository.save(user);
        postRepository.save(postone);
        postRepository.save(posttwo);

        List<Post> result = postRepository.findAllByUser_id(userId);

        List<Post> expected = new ArrayList<>();
        expected.add(postone);
        expected.add(posttwo);
        for (int i = 0; i < result.size(); i++) {
            assertThat(result.get(i)).usingRecursiveComparison().isEqualTo(expected.get(i));
        }
    }
}