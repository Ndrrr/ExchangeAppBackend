package com.exchange.app.service;

import com.exchange.app.domain.User;
import com.exchange.app.dto.UserDto;
import com.exchange.app.dto.request.LoginRequest;
import com.exchange.app.dto.request.RegisterRequest;
import com.exchange.app.dto.response.LoginResponse;
import com.exchange.app.error.BaseException;
import com.exchange.app.error.ErrorCode;
import com.exchange.app.error.UserNotFoundException;
import com.exchange.app.mapper.UserMapper;
import com.exchange.app.repository.UserRepository;
import com.exchange.app.security.JwtGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final JwtGenerator jwtGenerator;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public void register(RegisterRequest registerRequest) {
        userRepository.save(userMapper.toUser(registerRequest));
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw BaseException.of(ErrorCode.INVALID_CREDENTIALS, "Incorrect email or password");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);
        return LoginResponse.of(token);
    }

    public UserDto getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(userMapper::toUserDto)
                .orElseThrow(() ->
                        UserNotFoundException.of(ErrorCode.USER_NOT_FOUND, "User not found"));
    }

}
