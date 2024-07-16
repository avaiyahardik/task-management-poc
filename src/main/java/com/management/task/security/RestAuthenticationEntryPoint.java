package com.management.task.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * Custom implementation of AuthenticationEntryPoint for handling authentication failures.
 * Logs the error and sends an HTTP response with status code 401 (Unauthorized).
 */
@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * Invoked when authentication fails. Logs the error and sends an HTTP response with status code 401 (Unauthorized).
     *
     * @param httpServletRequest  The HTTP request that resulted in an AuthenticationException.
     * @param httpServletResponse The HTTP response to be updated with the status code and error message.
     * @param e                   The AuthenticationException that caused the authentication failure.
     * @throws IOException If an input or output exception occurs.
     */
    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException {
        log.error("Responding with unauthorized error. Message - {}", e.getMessage(), e);
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                e.getLocalizedMessage());
    }
}
