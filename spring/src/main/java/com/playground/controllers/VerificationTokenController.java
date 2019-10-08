package com.playground.controllers;

import com.playground.model.dto.UserDto;
import com.playground.model.entity.Role;
import com.playground.model.entity.User;
import com.playground.model.entity.VerificationToken;
import com.playground.repository.RoleRepository;
import com.playground.repository.UserRepository;
import com.playground.repository.VerificationTokenRepository;
import com.playground.utils.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/verification_token")
public class VerificationTokenController {

    @Inject
    private UserRepository userRepository;

    @Inject
    private RoleRepository roleRepository;

    @Inject
    private VerificationTokenRepository verificationTokenRepository;

    @GetMapping(value = "/{token}")
    public ResponseEntity<UserDto> verify(@PathVariable(value = "token") String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token not found"));
        Role role = roleRepository.findByName("ROLE_USER").get();
        User user = userRepository.findById(verificationToken.getUser().getId()).get();
        user.setRole(role);
        user = userRepository.save(user);
        return new ResponseEntity<>(new UserDto(user.getId(), user.getUsername()), HttpStatus.OK);
    }

}
