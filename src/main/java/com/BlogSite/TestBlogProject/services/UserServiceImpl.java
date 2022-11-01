package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.Dto.UserDto;
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
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public ResponseEntity<?> addUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()).isPresent())
            return ResponseEntity.badRequest().body("ALREADY_EXISTS");
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user = userRepository.save(user);
        return ResponseEntity.ok().body(user);
    }
}
