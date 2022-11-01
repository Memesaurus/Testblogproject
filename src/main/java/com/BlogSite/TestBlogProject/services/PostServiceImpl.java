package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.Dto.PostDto;
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
    private UserService userService;

    @Override
    public List<Post> getPostsByUsername(String username) {
        return postRepository.findAllByUser_id(username);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public ResponseEntity<?> addPost(PostDto postDto) {
        User user = userService.getUserByUsername(postDto.getUsername());
        if (user == null)
            return ResponseEntity.badRequest().body("USERNOTFOUND");
        Post post = new Post();
        post.setBody(postDto.getBody());
        post.setUser(user);
        post = postRepository.save(post);
        return ResponseEntity.ok().body(post);
    }
}
