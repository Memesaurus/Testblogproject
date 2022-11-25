package com.BlogSite.TestBlogProject.annotations;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockMyUserDetailsContextFactory.class)
public @interface WithMockMyUserDetails {
    long id() default 1L;
    String username() default "Test";
    String email() default "Test";
    String password() default "Test";
    String role() default "ROLE_USER";
}
