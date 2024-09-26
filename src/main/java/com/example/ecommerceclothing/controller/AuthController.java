package com.example.ecommerceclothing.controller;

import com.example.ecommerceclothing.dto.AuthResponse;
import com.example.ecommerceclothing.model.User;
import com.example.ecommerceclothing.service.JwtUtil;
import com.example.ecommerceclothing.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.saveUser(user);  // Save user with encrypted password
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> login(@RequestBody User loginRequest) {
        // Authenticate user
        User user = userService.findByUsername(loginRequest.getUsername())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse("Invalid credentials", null, null));
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(user.getUsername());

        // Create the response object with token and other details
        AuthResponse response = new AuthResponse("Login successful", token, user.getUsername());

        return ResponseEntity.ok(response);
    }

}
