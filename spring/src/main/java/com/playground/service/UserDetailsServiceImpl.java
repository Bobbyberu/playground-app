package com.playground.service;

import com.playground.model.entity.User;
import com.playground.repository.UserRepository;
import com.playground.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String mail) throws UsernameNotFoundException {
        User user = userRepository.findByMail(mail).orElseThrow(
                () -> new ResourceNotFoundException("User with mail " + mail + " not found"));
        return user;
    }
}
