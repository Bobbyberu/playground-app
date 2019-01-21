package com.playground.config;

import com.playground.security.CorsFilter;
import com.playground.security.JWTAuthenticationFilter;
import com.playground.security.JWTLoginFilter;
import com.playground.service.TokenAuthenticationService;
import com.playground.service.UserDetailsServiceImpl;
import com.playground.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private TokenAuthenticationService tokenAuthenticationService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable().authorizeRequests()
                // No need authentication.
                .antMatchers(HttpMethod.POST, "/users/signup").permitAll()
                .antMatchers(HttpMethod.POST, "/users/login").permitAll()
                .antMatchers(HttpMethod.GET, "/playgrounds/**").permitAll()
                .antMatchers(HttpMethod.GET, "/users/{userMail}/image").permitAll()
                // Need authentication.
                .anyRequest().authenticated()
                //
                .and()
                //
                // Add filter 1 - CorsFilter
                //
                .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
                //
                // Add Filter 2 - JWTLoginFilter
                //
                .addFilterBefore(new JWTLoginFilter("/users/login", authenticationManager(), tokenAuthenticationService),
                        UsernamePasswordAuthenticationFilter.class)
                //
                // Add Filter 3 - JWTAuthenticationFilter
                //
                .addFilterBefore(new JWTAuthenticationFilter(tokenAuthenticationService), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    // multipart file resolver to help upload images
    @Bean
    public MultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

    // init file storage
    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.initAll();
        };
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService);

    }

}
