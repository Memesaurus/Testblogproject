package com.BlogSite.TestBlogProject.controllers;

import com.BlogSite.TestBlogProject.dto.AuthRequestDto;
import com.BlogSite.TestBlogProject.dto.UserDto;
import com.BlogSite.TestBlogProject.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authentication;

    @PostMapping
    public Result<UserDto> loginPost(@RequestBody AuthRequestDto authRequestDto) {
        Result<UserDto> result = new Result<>();
        Authentication auth;
        try {
            auth = authentication.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequestDto.getUsername(),
                            authRequestDto.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            UserDto userDto = new UserDto();
            MyUserDetails userDetails = (MyUserDetails) auth.getPrincipal();

            userDto.setId(userDetails.getUser().getId());
            userDto.setUsername(authRequestDto.getUsername());
            if (auth.getAuthorities().contains(Roles.ROLE_ADMIN.getRole())) {
                userDto.setRole(Roles.ROLE_ADMIN.getRole());
            } else {
                userDto.setRole(Roles.ROLE_USER.getRole());
            }
            result.setData(userDto);
        } catch (BadCredentialsException e) {
            result.setError(ErrorCode.NOT_FOUND);
        }
        
        return result;
    }
}
