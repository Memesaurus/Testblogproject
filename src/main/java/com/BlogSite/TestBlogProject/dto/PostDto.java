package com.BlogSite.TestBlogProject.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PostDto implements Serializable {
    private String body;
    private String username;
}
