package com.BlogSite.TestBlogProject.annotations;

import com.BlogSite.TestBlogProject.models.MyUserDetails;
import com.BlogSite.TestBlogProject.models.Role;
import com.BlogSite.TestBlogProject.models.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockMyUserDetailsContextFactory
implements WithSecurityContextFactory<WithMockMyUserDetails> {
    @Override
    public SecurityContext createSecurityContext(WithMockMyUserDetails annotation) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        Role mockRole = new Role(1L, annotation.role());
        User mockUser = new User(annotation.id(),
                annotation.username(),
                annotation.email(),
                annotation.password(),
                true,
                mockRole);

        MyUserDetails principal = new MyUserDetails(mockUser);
        Authentication auth =
                UsernamePasswordAuthenticationToken
                        .authenticated(principal, principal.getPassword(), principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}
