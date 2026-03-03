package com.example.employeemanagement.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int status;         // HTTP status code (e.g., 404, 500)
    private String message;     // Error message to be sent to the client
    private LocalDateTime timestamp; // Time when the error occurred
}
