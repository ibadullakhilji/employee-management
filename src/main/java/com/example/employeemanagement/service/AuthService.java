package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.AuthRequestDTO;
import com.example.employeemanagement.dto.AuthResponseDTO;
import com.example.employeemanagement.model.User;
import com.example.employeemanagement.repository.UserRepository;
import com.example.employeemanagement.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO register(AuthRequestDTO dto){
        // Check if username already exists
        if (userRepository.findByUsername(dto.getUsername()).isPresent()){
            throw new RuntimeException("Username already exists");
    }

        // Create new user with Encoded password
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));    // // encrypt password!
        user.setRole("ROLE_USER");
        userRepository.save(user);

        // Generate And Return JWT Token
        return new AuthResponseDTO(jwtService.generateToken(dto.getUsername()));
    }

    public AuthResponseDTO login( AuthRequestDTO dto){
        // Spring Security verifies username and password using AuthenticationManager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername() , dto.getPassword())
        );

        // If we reach here credential are valid - Generate And Return JWT Token
        return new AuthResponseDTO(jwtService.generateToken(dto.getUsername()));
    }
}
