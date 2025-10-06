package com.polling_system.Polling_System.controllers;

// --- Imports from your existing packages ---
import com.polling_system.Polling_System.models.Roles;
import com.polling_system.Polling_System.models.User;
import com.polling_system.Polling_System.repository.UserRepository;
import com.polling_system.Polling_System.security.JWTTokenProvider;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    // The RoleRepository has been removed as per your request.
    // @Autowired
    // RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JWTTokenProvider jwtTokenProvider;

    /**
     * Handles user sign-in.
     * Accepts a JSON object with "username" and "password" keys.
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication.getName());

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Create a Map to hold the response data, avoiding a DTO.
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("token", jwt);
        responseBody.put("type", "Bearer");
        responseBody.put("id", user.getId());
        responseBody.put("username", userDetails.getUsername());
        responseBody.put("roles", roles);

        return ResponseEntity.ok(responseBody);
    }

    /**
     * Handles new user registration.
     * Accepts a JSON object matching the structure of your User entity.
     */
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            // Returns a Map for the error message, avoiding a DTO.
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Error: Username is already taken!"));
        }

        // Create a new user account from the request body
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));

        // NOTE: Because there is no RoleRepository, new users are created with NO roles.
        // For any role-based authorization to work, you will need to add roles
        // to the user manually in the database.
        user.setRoles(new HashSet<>());

        userRepository.save(user);

        // Returns a Map for the success message, avoiding a DTO.
        return ResponseEntity.ok(Map.of("message", "User registered successfully! (Note: no roles assigned)"));
    }
}

