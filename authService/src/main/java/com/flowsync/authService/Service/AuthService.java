package com.flowsync.authService.Service;


import com.flowsync.authService.client.CreateUserProfileRequest;
import com.flowsync.authService.client.UserResponse;
import com.flowsync.authService.domain.AuthUser;
import com.flowsync.authService.dto.Request.LoginRequest;
import com.flowsync.authService.dto.Request.RegisterRequest;
import com.flowsync.authService.dto.Response.JwtResponse;
import com.flowsync.authService.jwt.JwtService;
import com.flowsync.authService.repository.AuthUserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.beans.Transient;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RestTemplate restTemplate;


    @Transactional
    public JwtResponse register(RegisterRequest request) {
        log.info("Processing registration request for email: {}", request.getEmail());

        // Checking  if user already exists
        if (authUserRepository.existsByEmail(request.getEmail())) {
            log.warn("Registration failed: Email already exists - {}", request.getEmail());
            throw new IllegalArgumentException("Email already registered");
        }
        // Create new user
        UUID userId = UUID.randomUUID();
        AuthUser authUser = new AuthUser(
                userId,
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getRole()
        );
        authUserRepository.save(authUser);

        CreateUserProfileRequest UserProfile = new CreateUserProfileRequest(
                userId
                ,request.getFirstName()
                ,request.getLastName()
                ,request.getEmail()
        );


        UserResponse userResponse = restTemplate.postForObject("http://localhost:8082/users/internal"
                , UserProfile, UserResponse.class);
        log.info("User profile saved and response received from : User microservice{}", userResponse);


        String token = jwtService.generateToken(authUser);
        log.info("Authentication token generated: {}", token);

        return JwtResponse.builder()
                .token(token)
                .userId(authUser.getId())
                .email(authUser.getEmail())
                .role(authUser.getRole().name())
                .description(String.valueOf(userResponse))
                .build();
    }



    public JwtResponse login(LoginRequest request) {
        log.info("Processing login request for email: {}", request.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        authUser.updateLastLogin();
        authUserRepository.save(authUser);

        String token = jwtService.generateToken(authUser);

        log.info("User logged in successfully: {}", request.getEmail());

        return JwtResponse.builder()
                .token(token)
                .userId(authUser.getId())
                .email(authUser.getEmail())
                .role(authUser.getRole().name())
                .description("This Payload Contains JWT TOKEN")
                .build();
    }

    public boolean validateToken(String token) {
        try {
            boolean isValid = jwtService.validateToken(token);
            log.debug("Token validation result: {}", isValid);
            return isValid;
        } catch (Exception e) {
            log.error("Token validation failed", e);
            return false;
        }
    }




}
