package com.BlogSite.TestBlogProject.repositories;

import com.BlogSite.TestBlogProject.models.Post;
import com.BlogSite.TestBlogProject.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatObject;

@DataJpaTest
class PostRepositoryTest {

    @Autowired
    private PostRepository test;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void tearDown() {
        test.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void ShouldFindAllUserPostsByGivenUsername() {
        String username = "TestUser";
        String email = "TestUser@test.com";
        User user = new User(1L,
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
        test.save(postone);
        test.save(posttwo);

        List<Post> result = test.findAllByUser_id(username);

        List<Post> expected = new ArrayList<>();
        expected.add(postone);
        expected.add(posttwo);
        for(int i = 0; i < result.size(); i++){
            assertThat(result.get(i)).usingRecursiveComparison().isEqualTo(expected.get(i));
        }
    }
}