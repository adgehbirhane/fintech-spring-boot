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

        // Check if the exception is related to missing or invalid token
        String errorMessage = "Unauthorized: Missing or invalid token.";

        // You can further customize here by checking for specific exceptions
        if (authException instanceof org.springframework.security.authentication.BadCredentialsException) {
            errorMessage = "Unauthorized: Invalid or missing token.";
        }

        // Set the response status and return the error message as JSON
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
//        response.setContentType("application/json"); // return as JSON
//        response.getWriter().write("{\"statusCode\":401,\"message\": \"" + errorMessage + "\"}");
//        response.getWriter().flush(); // Make sure the response is sent
    }
}
