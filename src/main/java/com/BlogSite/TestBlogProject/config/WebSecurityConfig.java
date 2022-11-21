package com.BlogSite.TestBlogProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().disable();
        http.formLogin()
                .loginPage("/api/login")
                .permitAll()
                .and().logout()
                .logoutUrl("/api/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**")
                .permitAll()
                .and().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/posts/**")
                .authenticated()
                .and().authorizeRequests()
                .antMatchers("/api/admin/**")
                .hasRole("ADMIN");
        return http.build();
    }
}
