package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.Dto.UserDto;
import com.BlogSite.TestBlogProject.models.ErrorCode;
import com.BlogSite.TestBlogProject.models.Result;
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
    public Result<User> getUser(Long id) {
        Result<User> result = new Result<>();
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            result.setError(ErrorCode.USER_NOT_FOUND);
            return result;
        }
        result.setData(user);
        return result;
    }

    @Override
    public Result<User> getUserByUsername(String username) {
        Result<User> result = new Result<>();
        User user = userRepository.findByUsername(username).orElse(null);
        if(user == null) {
            result.setError(ErrorCode.USER_NOT_FOUND);
            return result;
        }
        result.setData(user);
        return result;
    }

    @Override
    public Result<ResponseEntity<?>> addUser(UserDto userDto) {
        Result<ResponseEntity<?>> result = new Result<>();
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            result.setError(ErrorCode.ALREADY_EXISTS);
            return result;
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user = userRepository.save(user);
        result.setData(ResponseEntity.ok().body(user));
        return result;
    }
}
