package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.DTO.PostDto;
import com.BlogSite.TestBlogProject.models.Post;
import com.BlogSite.TestBlogProject.models.User;
import com.BlogSite.TestBlogProject.repositories.PostRepository;
import com.BlogSite.TestBlogProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Post> getPostsByUsername(String username) {
        Long userid = userRepository.findByUsername(username).getId();
        return postRepository.findAllByUser_id(userid);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public ResponseEntity<?> postPost(PostDto postDto) {
        Post post = new Post();
        post.setBody(postDto.getBody());
        User user = userRepository.findByUsername(postDto.getUsername());
        post.setUser(user);
        post = postRepository.save(post);
        return ResponseEntity.ok().body(post);
    }
}
