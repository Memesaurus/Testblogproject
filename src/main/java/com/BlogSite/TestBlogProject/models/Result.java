package com.BlogSite.TestBlogProject.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result<T> {
    private T data;
    private ErrorCode error;
}
