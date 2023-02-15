package com.exchange.app.security;

import com.exchange.app.error.UserNotFoundException;
import com.exchange.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.exchange.app.domain.User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("n", "Email not found"));
        return new User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }

}
