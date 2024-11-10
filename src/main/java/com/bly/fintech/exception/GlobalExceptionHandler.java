package com.bly.fintech.exception;

import com.bly.fintech.dto.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.warn("Handled IllegalArgumentException: {}", ex.getMessage());

        ApiResponse errorResponse = new ApiResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(Exception ex) {
//        logger.error("Handled generic exception: {}", ex.getMessage());

        ApiResponse errorResponse = new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    // Handle 404 Not Found (NoHandlerFoundException)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse> handleNotFoundException(NoHandlerFoundException ex) {
//        logger.warn("Handled 404 Not Found: {}", ex.getMessage());

        ApiResponse errorResponse = new ApiResponse(HttpStatus.NOT_FOUND.value(), "Resource not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse> handleResponseStatusException(ResponseStatusException ex) {
//        logger.warn("Handled ResponseStatusException: {}", ex.getReason());

        ApiResponse errorResponse = new ApiResponse(ex.getStatusCode().value(), ex.getReason());
        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String message;

        if (ex.getRootCause() != null && ex.getRootCause().getMessage().contains("users_username_key")) {
            message = "Username already exists. Please choose a different username.";
        } else if (ex.getRootCause() != null && ex.getRootCause().getMessage().contains("users_email_key")) {
            message = "Email already exists. Please use a different email.";
        } else {
            message = "Database error: " + ex.getRootCause().getMessage();
        }

        ApiResponse errorResponse = new ApiResponse(HttpStatus.CONFLICT.value(), message);
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

}
