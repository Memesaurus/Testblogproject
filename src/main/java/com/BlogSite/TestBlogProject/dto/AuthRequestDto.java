package com.BlogSite.TestBlogProject.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthRequestDto implements Serializable {
    private String username;
    private String email;
    private String password;
}
