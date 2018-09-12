package com.jariwala.securityjwtstarter.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    private String status;

    private String message;

    private boolean validationError;

    private String token;

    private String validationErrorMessage;

    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public ApiResponse(String status, String message, String token) {
        this.status = status;
        this.message = message;
        this.token = token;
    }

    public ApiResponse(boolean validationError, String validationErrorMessage) {
        this.validationError = validationError;
        this.validationErrorMessage = validationErrorMessage;
    }

    public static ResponseEntity<ApiResponse> build200(String message) {
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK.name(), message));
    }

    public static ResponseEntity<ApiResponse> validationError(String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse(true, message));
    }

    public static ResponseEntity<ApiResponse> successToken(String message, String token) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(HttpStatus.OK.name(), message, token));
    }
}
