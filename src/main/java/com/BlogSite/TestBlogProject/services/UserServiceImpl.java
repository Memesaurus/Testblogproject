package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.dto.AuthRequestDto;
import com.BlogSite.TestBlogProject.dto.UserDto;
import com.BlogSite.TestBlogProject.mapper.UserMapper;
import com.BlogSite.TestBlogProject.models.*;
import com.BlogSite.TestBlogProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserDto> getUsers() {
        List<User> userList = userRepository.findAll();
        return userMapper.userListToUserDtoList(userList);
    }

    @Override
    public String getAuthenticatedUserUsername() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication.getName();
    }

    @Override
    public Result<User> getUser(Long id) {
        Result<User> result = new Result<>();
        userRepository.findById(id).ifPresentOrElse(
                result::setData,
                () -> result.setError(ErrorCode.NOT_FOUND));
        return result;
    }

    @Override
    public Result<User> getUserByUsername(String username) {
        Result<User> result = new Result<>();
        userRepository.findByUsername(username).ifPresentOrElse(
                result::setData,
                () -> result.setError(ErrorCode.NOT_FOUND));
        return result;
    }

    @Override
    public Result<User> addUser(AuthRequestDto authRequestDto) {
        Result<User> result = new Result<>();
        if (userRepository.findByUsername(authRequestDto.getUsername()).isPresent()) {
            result.setError(ErrorCode.ALREADY_EXISTS);
        } else {
            User user = userMapper.loginDtoToUser(authRequestDto, Roles.ROLE_USER.getRole());
            user.encryptPassword(user.getPassword());
            user = userRepository.save(user);
            result.setData(user);
        }
        return result;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found!"));
        return new MyUserDetails(user);
    }
}
