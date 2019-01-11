package com.playground.security;

import com.playground.service.TokenAuthenticationService;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Expose-Headers", "*");

        String jsonString = IOUtils.toString(request.getInputStream(), Charset.defaultCharset());
        if (!StringUtils.isEmpty(jsonString)) {
            JSONObject json = new JSONObject(jsonString);

        username = json.getString("mail");
        password = json.getString("password");

            System.out.printf("JWTLoginFilter.attemptAuthentication: username/password= %s,%s", username, password);
            System.out.println();
        } else {
            return null;
        }

        return getAuthenticationManager()
                .authenticate(new UsernamePasswordAuthenticationToken(username, password, Collections.emptyList()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) {

        // Write Authorization to Headers of Response.
        TokenAuthenticationService.addAuthentication(response, authResult.getName());
    }

}