package com.management.task.service;

import com.management.task.exception.InvalidRefreshTokenException;
import com.management.task.security.TokenProvider;
import com.management.task.security.UserPrincipal;
import com.management.task.security.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Service class that handles authentication operations such as user sign up, sign in, and token refresh.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    /**
     * Registers a new user based on the provided sign-up request.
     *
     * @param signUpRequest The request containing user registration details.
     */
    public void signUp(SignUpRequest signUpRequest) {
        userService.createUser(signUpRequest);
    }

    /**
     * Authenticates a user based on the provided sign-in request and generates a JWT authentication token.
     *
     * @param signInRequest The request containing user credentials for authentication.
     * @return A {@link JwtResponse} containing the JWT authentication token.
     */
    public JwtResponse signIn(SignInRequest signInRequest) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword());
        var authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        var userPrincipal = (UserPrincipal) authentication.getPrincipal();
        var token = tokenProvider.createAccessToken(authentication);
        return prepareJwtResponse(token, userPrincipal);
    }

    /**
     * Prepares a {@link JwtResponse} object based on the JWT token and user principal.
     *
     * @param token         The JWT access token.
     * @param userPrincipal The authenticated user principal.
     * @return A {@link JwtResponse} object containing the JWT token and user details.
     */
    private JwtResponse prepareJwtResponse(String token, UserPrincipal userPrincipal) {
        return JwtResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .id(userPrincipal.getId())
                .firstname(userPrincipal.getFirstname())
                .lastname(userPrincipal.getLastname())
                .email(userPrincipal.getEmail())
                .roles(userPrincipal.getRoles())
                .refreshToken(tokenProvider.createRefreshToken(userPrincipal.getId()))
                .build();
    }

    /**
     * Refreshes the JWT access token using the provided refresh token.
     *
     * @param refreshTokenRequest The request containing the refresh token.
     * @return A {@link RefreshTokenResponse} containing the new JWT access token.
     * @throws InvalidRefreshTokenException If the provided refresh token is invalid.
     */
    public RefreshTokenResponse refresh(RefreshTokenRequest refreshTokenRequest) {
        if (tokenProvider.validateRefreshToken(refreshTokenRequest.getRefreshToken())) {
            var id = tokenProvider.getUserIdFromRefreshToken(refreshTokenRequest.getRefreshToken());
            return RefreshTokenResponse.builder()
                    .accessToken(tokenProvider.createAccessToken(id))
                    .refreshToken(tokenProvider.createRefreshToken(id))
                    .tokenType("Bearer")
                    .build();
        }
        throw new InvalidRefreshTokenException(refreshTokenRequest.getRefreshToken(), "Invalid refresh token");
    }

}
