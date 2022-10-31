package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.DTO.UserDto;
import com.BlogSite.TestBlogProject.models.User;
import com.BlogSite.TestBlogProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public ResponseEntity<?> postUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()) == null) {
            User user = new User();
            user.setUsername(userDto.getUsername());
            user.setEmail(userDto.getEmail());
            user = userRepository.save(user);
            return ResponseEntity.ok().body(user);
        }
        return ResponseEntity.badRequest().body("ALRDY_EXISTS");
    }
}
