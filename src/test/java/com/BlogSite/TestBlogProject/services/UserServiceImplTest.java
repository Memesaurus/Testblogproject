package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.dto.AuthRequestDto;
import com.BlogSite.TestBlogProject.dto.UserDto;
import com.BlogSite.TestBlogProject.mapper.UserMapper;
import com.BlogSite.TestBlogProject.models.ErrorCode;
import com.BlogSite.TestBlogProject.models.Role;
import com.BlogSite.TestBlogProject.models.Roles;
import com.BlogSite.TestBlogProject.models.User;
import com.BlogSite.TestBlogProject.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUsers_ShouldReturnAllUsers() {
        Role role = new Role(1L, "ROLE_TEST");
        User user = new User(1L,
                "Test",
                "Test",
                "Test",
                true,
                role);
        List<User> userList = List.of(user);
        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setUsername("Test");
        expectedUserDto.setRole(role);
        List<UserDto> expectedList = List.of(expectedUserDto);

        doReturn(userList)
                .when(userRepository).findAll();
        doReturn(expectedList)
                .when(userMapper).userListToUserDtoList(userList);
        List<UserDto> actualList = userService.getUsers();

        Assertions.assertEquals(expectedList, actualList);
    }

    @Test
    void getUser_ShouldGetUserById() {
        Long id = 1L;
        User expectedUser = new User(id,
                "Test",
                "Test",
                "Test",
                true,
                null);

        doReturn(Optional.of(expectedUser))
                .when(userRepository).findById(id);
        User actualUser = userService.getUser(id).getData();

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    void getUser_ShouldReturnErrorCode() {
        long id = 0;
        ErrorCode expectedError = ErrorCode.NOT_FOUND;

        doReturn(Optional.empty())
                .when(userRepository).findById(id);
        ErrorCode actualError = userService.getUser(id).getError();

        Assertions.assertEquals(expectedError, actualError);
    }

    @Test
    void getUserByUsername_ShouldGetUserByUsername() {
        String username = "Test";
        User expectedUser = new User(1L,
                username,
                username,
                username,
                true,
                null);

        doReturn(Optional.of(expectedUser))
                .when(userRepository).findByUsername(username);
        User actualUser = userService.getUserByUsername(username).getData();

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    void getUserByUsername_ShouldReturnErrorCode() {
        String username = "";
        ErrorCode expectedError = ErrorCode.NOT_FOUND;

        doReturn(Optional.empty())
                .when(userRepository).findByUsername(username);
        ErrorCode actualError = userService.getUserByUsername(username).getError();

        Assertions.assertEquals(expectedError, actualError);
    }

    @Test
    void addUser_ShouldSaveUser() {
        String username = "Test";
        User expectedUser = new User(1L,
                username,
                username,
                username,
                true,
                null);
        User mockUser = new User(null,
                username,
                username,
                username,
                true,
                null);
        AuthRequestDto authRequestDto = new AuthRequestDto();
        authRequestDto.setUsername(username);
        authRequestDto.setEmail(username);

        doReturn(Optional.empty())
                .when(userRepository).findByUsername(username);
        doReturn(mockUser)
                .when(userMapper).loginDtoToUser(authRequestDto, Roles.ROLE_USER.getRole());
        doReturn(expectedUser)
                .when(userRepository).save(mockUser);
        User actualUser = userService.addUser(authRequestDto).getData();

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    void addUser_ShouldReturnErrorCode() {
        AuthRequestDto authRequestDto = new AuthRequestDto();
        ErrorCode expectedError = ErrorCode.ALREADY_EXISTS;

        doReturn(Optional.of(new User()))
                .when(userRepository).findByUsername(authRequestDto.getUsername());
        ErrorCode actualError = userService.addUser(authRequestDto).getError();

        Assertions.assertEquals(expectedError, actualError);
    }
}