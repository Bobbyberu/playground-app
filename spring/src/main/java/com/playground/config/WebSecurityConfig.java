package com.playground.config;

import com.playground.security.CorsFilter;
import com.playground.security.JWTAuthenticationFilter;
import com.playground.security.JWTLoginFilter;
import com.playground.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("configure");
        http.csrf().disable().cors().disable().authorizeRequests()
                // No need authentication.
                .antMatchers(HttpMethod.POST, "/users/signup").permitAll()
                .antMatchers(HttpMethod.POST, "/users/login").permitAll()
                .antMatchers(HttpMethod.GET, "/playgrounds/**").permitAll()
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
                .addFilterBefore(new JWTLoginFilter("/users/login", authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                //
                // Add Filter 3 - JWTAuthenticationFilter
                //
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService);

    }

}
