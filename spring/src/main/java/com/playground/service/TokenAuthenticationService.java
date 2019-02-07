package com.playground.service;

import com.playground.model.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
public class TokenAuthenticationService {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    static final long EXPIRATIONTIME = 864_000_000; // 10 days

    public static final String SECRET = "ThisIsASecret";

    public static final String TOKEN_PREFIX = "Bearer";

    static final String HEADER_STRING = "Authorization";

    public void addAuthentication(HttpServletResponse res, User user) {
        String JWT = Jwts.builder().setSubject(user.getMail())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .claim("role", user.getRole().getName())
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            User user = getUser(token);

            return user != null ? new UsernamePasswordAuthenticationToken(user.getMail(), user.getPassword(), user.getAuthorities()) : null;
        }
        return null;
    }

    public User getUser(String token) {
        String username = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody()
                .getSubject();
        return userDetailsService.loadUserByUsername(username);
    }

}