package com.BlogSite.TestBlogProject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Roles {
    ROLE_ADMIN(new Role(1L, "ROLE_ADMIN")),
    ROLE_USER(new Role(2L, "ROLE_USER"));

    private final Role role;
}
