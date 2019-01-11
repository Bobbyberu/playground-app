package com.playground.controllers;

import com.playground.model.Comment;
import com.playground.model.Role;
import com.playground.model.User;
import com.playground.model.VerificationToken;
import com.playground.repository.RoleRepository;
import com.playground.repository.UserRepository;
import com.playground.repository.VerificationTokenRepository;
import com.playground.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verification_token")
public class VerificationTokenController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @GetMapping(value = "/{token}")
    public ResponseEntity<User> verify(@PathVariable(value = "token") String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElseThrow(() -> new ResourceNotFoundException("Token not found"));
        Role role = roleRepository.findByName("USER").get();
        User user = userRepository.findById(verificationToken.getUser().getId()).get();
        user.setRole(role);
        user = userRepository.save(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
