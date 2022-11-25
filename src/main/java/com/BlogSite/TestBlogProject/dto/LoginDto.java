package com.BlogSite.TestBlogProject.dto;

import com.BlogSite.TestBlogProject.models.Role;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoginDto implements Serializable {
    private String username;
    private String email;
    private String password;
    private Role role;
}
