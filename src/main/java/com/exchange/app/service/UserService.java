package com.exchange.app.service;

import com.exchange.app.token.Token;
import com.exchange.app.token.TokenType;
import com.exchange.app.domain.User;
import com.exchange.app.dto.UserDto;
import com.exchange.app.dto.request.ForgotPasswordRequest;
import com.exchange.app.dto.request.LoginRequest;
import com.exchange.app.dto.request.RegisterRequest;
import com.exchange.app.dto.request.ResetPasswordRequest;
import com.exchange.app.dto.response.LoginResponse;
import com.exchange.app.error.BaseException;
import com.exchange.app.error.ErrorCode;
import com.exchange.app.error.UserNotFoundException;
import com.exchange.app.mapper.UserMapper;
import com.exchange.app.repository.TokenRepository;
import com.exchange.app.repository.UserRepository;
import com.exchange.app.util.JwtUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender javaMailSender;
    private final Environment env;

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
        var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();
        String token = jwtUtil.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, token);
        return LoginResponse.of(token);
    }

    public void forgotPassword(ForgotPasswordRequest request) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        String email = request.getEmail();
        try {
            helper.setFrom(env.getProperty("spring.mail.username"));
            helper.setSubject("Password reset");
            helper.setTo(email);
            helper.setText(generatePasswordResetLink(email));
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw BaseException.of(ErrorCode.EMAIL_SENDING_ERROR, "Error while sending email");
        }
    }

    public void updatePassword(String token, ResetPasswordRequest request) {
        if (!jwtUtil.validateToken(token)) {
            throw BaseException.of(ErrorCode.INVALID_TOKEN, "Invalid token");
        }
        String email = jwtUtil.getUsernameFromJWT(token);
        User user = userRepository.findByEmail(email).get();
        user.setPassword(userMapper.encodePassword(request.getPassword()));
        userRepository.save(user);
    }

    public UserDto getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(userMapper::toUserDto)
                .orElseThrow(() ->
                        UserNotFoundException.of(ErrorCode.USER_NOT_FOUND, "User not found"));
    }

    private String generatePasswordResetLink(String email) {
        // #TODO create whitelist for password reset tokens
        return "Dear user,\n\nPlease click the following link to reset your password." +
                String.format(
                        "\nhttp://%s:%s/reset-password/%s",
                        env.getProperty("front.host"),
                        env.getProperty("front.port"),
                        jwtUtil.generateToken(email,
                                env.getProperty("jwt.expiration.reset-password", Long.class))
                ) +
                "\nIf you did not request this, please ignore this email." +
                "Sincerely,\n\nExchangeApp Team.";
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty()) return;
        validUserTokens.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

}
