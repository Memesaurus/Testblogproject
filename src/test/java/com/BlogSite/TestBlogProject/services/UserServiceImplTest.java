package com.BlogSite.TestBlogProject.services;

import com.BlogSite.TestBlogProject.Dto.UserDto;
import com.BlogSite.TestBlogProject.models.ErrorCode;
import com.BlogSite.TestBlogProject.models.User;
import com.BlogSite.TestBlogProject.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl test;

    @Test
    void getUsers_ShouldReturnAllUsers() {
        test.getUsers();

        verify(userRepository).findAll();
    }

    @Test
    void getUser_ShouldGetUserById() {
        Long id = 1L;

        test.getUser(id);

        ArgumentCaptor<Long> longArgumentCaptor =
                ArgumentCaptor.forClass(Long.class);
        verify(userRepository).findById(longArgumentCaptor.capture());
        Long result = longArgumentCaptor.getValue();
        assertThat(result).isEqualTo(id);
    }

    @Test
    void addUser_ShouldSaveUser() {
        String username = "Test";
        String email = "email";
        UserDto userDto = new UserDto();
        userDto.setEmail(email);
        userDto.setUsername(username);
        User expected = new User(null,
                username,
                email
        );

        test.addUser(userDto);

        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void addUser_ShouldReturnResponseEntityBADREQUEST() {
        UserDto userDto = new UserDto();
        userDto.setUsername("Test");
        ErrorCode expectedResponse = ErrorCode.ALREADY_EXISTS;

        doReturn(Optional.of(new User())).when(userRepository).findByUsername(userDto.getUsername());
        ResponseEntity<?> response = test.addUser(userDto);

        assertThat(response.getBody()).usingRecursiveComparison().isEqualTo(expectedResponse);
    }
}