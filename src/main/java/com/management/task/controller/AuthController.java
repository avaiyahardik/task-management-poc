package com.management.task.controller;

import com.management.task.model.common.MessageResponse;
import com.management.task.security.model.*;
import com.management.task.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling authentication-related operations such as sign up, sign in, and token refresh.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Endpoint for user registration.
     *
     * @param signUpRequest The request body containing user registration details.
     * @return A {@link MessageResponse} indicating the result of the registration process.
     */
    @PostMapping("/signup")
    public MessageResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        authService.signUp(signUpRequest);
        return new MessageResponse("User registered successfully!");
    }

    /**
     * Endpoint for user authentication (sign in).
     *
     * @param signInRequest The request body containing user credentials for authentication.
     * @return A {@link JwtResponse} containing the JWT authentication token.
     */
    @PostMapping("/signin")
    public JwtResponse signIn(@Valid @RequestBody SignInRequest signInRequest) {
        return authService.signIn(signInRequest);
    }

    /**
     * Endpoint for refreshing an authentication token.
     *
     * @param refreshTokenRequest The request body containing the refresh token.
     * @return A {@link RefreshTokenResponse} containing the new JWT authentication token.
     */
    @PostMapping("/refresh")
    public RefreshTokenResponse refresh(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refresh(refreshTokenRequest);
    }
}
