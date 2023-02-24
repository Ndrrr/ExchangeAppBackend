package com.exchange.app.controller;

import com.exchange.app.dto.request.ForgotPasswordRequest;
import com.exchange.app.dto.request.LoginRequest;
import com.exchange.app.dto.request.RegisterRequest;
import com.exchange.app.dto.request.ResetPasswordRequest;
import com.exchange.app.dto.response.LoginResponse;
import com.exchange.app.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @PostMapping("/register")
    public void register(@RequestBody @Valid RegisterRequest registerRequest) {
        userService.register(registerRequest);
    }

    @PostMapping("/forgot-password")
    public void forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        userService.forgotPassword(request);
    }

    @GetMapping("/reset-password/{token}")
    public void resetPassword(@PathVariable String token,
                              @RequestBody @Valid ResetPasswordRequest request) {
        userService.updatePassword(token, request);
    }

}
