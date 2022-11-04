package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.dto.UserDto;
import com.BlogSite.TestBlogProject.mapper.UserMapper;
import com.BlogSite.TestBlogProject.models.ErrorCode;
import com.BlogSite.TestBlogProject.models.Result;
import com.BlogSite.TestBlogProject.models.User;
import com.BlogSite.TestBlogProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Result<User> getUser(Long id) {
        Result<User> result = new Result<>();
        userRepository.findById(id).ifPresentOrElse(
                result::setData,
                () -> result.setError(ErrorCode.USER_NOT_FOUND));
        return result;
    }

    @Override
    public Result<User> getUserByUsername(String username) {
        Result<User> result = new Result<>();
        userRepository.findByUsername(username).ifPresentOrElse(
                result::setData,
                () -> result.setError(ErrorCode.USER_NOT_FOUND));
        return result;
    }

    @Override
    public Result<User> addUser(UserDto userDto) {
        Result<User> result = new Result<>();
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            result.setError(ErrorCode.ALREADY_EXISTS);
        } else {
            User user = userMapper.userDtoToUser(userDto);
            user = userRepository.save(user);
            result.setData(user);
        }
        return result;
    }
}
