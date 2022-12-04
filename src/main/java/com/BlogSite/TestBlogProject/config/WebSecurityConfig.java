package com.BlogSite.TestBlogProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.exceptionHandling()
                        .defaultAuthenticationEntryPointFor(new Http403ForbiddenEntryPoint(),
                                new AntPathRequestMatcher("/api/**"));
        http.formLogin()
                .loginPage("/api/login")
                .successForwardUrl("/api/login?success")
                .permitAll()
                .and().logout()
                .logoutUrl("/api/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and().authorizeRequests()
                .antMatchers("/api/admin/**")
                .hasRole("ADMIN")
                .and().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/users/**")
                .permitAll()
                .and().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/posts/**")
                .authenticated()
                .and().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/posts/**")
                .authenticated();
        return http.build();
    }
}
