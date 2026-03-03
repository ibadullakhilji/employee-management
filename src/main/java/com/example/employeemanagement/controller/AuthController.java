package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.AuthRequestDTO;
import com.example.employeemanagement.dto.AuthResponseDTO;
import com.example.employeemanagement.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag( name = "Authentication" , description = "API's for Registration and Login" )
public class AuthController {

    private final AuthService authService;

    @Operation( summary = "Register a new user")
    @PostMapping("/register")                   // Creates a new account , saves username + encrypted password in DB and returns JWT token
    public ResponseEntity<AuthResponseDTO> register(@RequestBody AuthRequestDTO dto){
        return ResponseEntity.ok(authService.register(dto));
    }

    @Operation(summary = "Login and get JWT token")
    @PostMapping("/login")      // Sign in to existing account ,Use this every time you want to get a fresh token
    public ResponseEntity<AuthResponseDTO> login (@RequestBody AuthRequestDTO dto){
        return ResponseEntity.ok(authService.login(dto));
    }

}

/*  FLOW SHUD BE
1. Register once → get token
2. Token expires after 24 hours
3. Login → get fresh token
4. Use token to hit all protected endpoints
5. Token expires again → Login again
*/
