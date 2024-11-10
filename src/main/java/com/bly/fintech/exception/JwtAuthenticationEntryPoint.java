package com.bly.fintech.exception;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        String errorMessage = "Unauthorized: Missing or invalid token.";

        if (authException instanceof org.springframework.security.authentication.BadCredentialsException) {
            errorMessage = "Unauthorized: Invalid or missing token.";
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
        response.setContentType("application/json"); // return as JSON
        response.getWriter().write("{\"statusCode\":401,\"message\": \"" + "errorMessage" + "\"}");
        response.getWriter().flush(); // Make sure the response is sent
    }
}
