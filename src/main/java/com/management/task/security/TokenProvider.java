package com.management.task.security;

import com.management.task.security.config.AppAuthConfig;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service class for managing JWT token creation, validation, and retrieval of user IDs from tokens.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TokenProvider {

    private final AppAuthConfig appAuthConfig;

    /**
     * Creates an access token based on the provided authentication object.
     *
     * @param authentication The authentication object containing user details.
     * @return A JWT access token string.
     */
    public String createAccessToken(Authentication authentication) {
        var userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return createAccessToken(userPrincipal.getId());
    }

    /**
     * Creates a refresh token based on the provided authentication object.
     *
     * @param authentication The authentication object containing user details.
     * @return A JWT refresh token string.
     */
    public String createRefreshToken(Authentication authentication) {
        var userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return createRefreshToken(userPrincipal.getId());
    }

    /**
     * Creates a refresh token for the specified user ID.
     *
     * @param id The user ID for whom the refresh token is created.
     * @return A JWT refresh token string.
     */
    public String createRefreshToken(long id) {
        return createBasicToken(Long.toString(id), appAuthConfig.getRefreshTokenExpiryInMs())
                .signWith(SignatureAlgorithm.HS512, appAuthConfig.getRefreshTokenSecret())
                .compact();
    }

    /**
     * Creates an access token for the specified user ID.
     *
     * @param id The user ID for whom the access token is created.
     * @return A JWT access token string.
     */
    public String createAccessToken(long id) {
        var idString = Long.toString(id);
        return createBasicToken(idString, appAuthConfig.getTokenExpiryInMs())
                .signWith(SignatureAlgorithm.HS512, appAuthConfig.getTokenSecret())
                .compact();
    }

    /**
     * Retrieves the user ID from the provided JWT token using the specified secret.
     *
     * @param token  The JWT token from which to extract the user ID.
     * @param secret The secret key used to validate and parse the JWT token.
     * @return The user ID extracted from the JWT token.
     * @throws JwtException If there is an error parsing or validating the JWT token.
     */
    public Long getUserIdFromToken(String token, String secret) {
        var claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    /**
     * Retrieves the user ID from the provided access token using the configured token secret.
     *
     * @param token The JWT access token from which to extract the user ID.
     * @return The user ID extracted from the access token.
     * @throws JwtException If there is an error parsing or validating the access token.
     */
    public Long getUserIdFromAccessToken(String token) {
        return getUserIdFromToken(token, appAuthConfig.getTokenSecret());
    }

    /**
     * Retrieves the user ID from the provided refresh token using the configured refresh token secret.
     *
     * @param token The JWT refresh token from which to extract the user ID.
     * @return The user ID extracted from the refresh token.
     * @throws JwtException If there is an error parsing or validating the refresh token.
     */
    public Long getUserIdFromRefreshToken(String token) {
        return getUserIdFromToken(token, appAuthConfig.getRefreshTokenSecret());
    }

    /**
     * Validates the JWT token using the specified secret.
     *
     * @param secret    The secret key used to validate the JWT token.
     * @param authToken The JWT token to validate.
     * @return True if the token is valid, false otherwise.
     */
    private boolean validateToken(String secret, String authToken) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature", ex);
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token", ex);
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token", ex);
        } catch (JwtException ex) {
            log.error("Expired JWT token", ex);
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.", ex);
        }
        return false;
    }

    /**
     * Creates a JWT builder instance initialized with standard claims.
     *
     * @param id     The subject (user ID) to set in the JWT token.
     * @param expiry The expiration time (in milliseconds) for the JWT token.
     * @return A JWT builder instance ready to be signed.
     */
    private JwtBuilder createBasicToken(String id, long expiry) {
        var now = new Date();
        var expiryDate = new Date(now.getTime() + expiry);

        return Jwts.builder()
                .setSubject(id)
                .setIssuedAt(now)
                .setExpiration(expiryDate);
    }

    /**
     * Validates the JWT access token using the configured token secret.
     *
     * @param authToken The JWT access token to validate.
     * @return True if the access token is valid, false otherwise.
     */
    public boolean validateAccessToken(String authToken) {
        return validateToken(appAuthConfig.getTokenSecret(), authToken);
    }

    /**
     * Validates the JWT refresh token using the configured refresh token secret.
     *
     * @param refreshAuthToken The JWT refresh token to validate.
     * @return True if the refresh token is valid, false otherwise.
     */
    public boolean validateRefreshToken(String refreshAuthToken) {
        return validateToken(appAuthConfig.getRefreshTokenSecret(), refreshAuthToken);
    }

}
