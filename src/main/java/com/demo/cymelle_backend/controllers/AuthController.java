package com.demo.cymelle_backend.controllers;

import com.demo.cymelle_backend.config.JwtUtil;
import com.demo.cymelle_backend.dtos.LoginRequest;
import com.demo.cymelle_backend.dtos.RegisterRequest;
import com.demo.cymelle_backend.entities.User;
import com.demo.cymelle_backend.repositories.RoleRepository;
import com.demo.cymelle_backend.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "Auth Controller", description = "Authentication related endpoints")
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        if (userRepository.findByPhone(request.getPhone()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: Phone is already in use!");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(encoder.encode(request.getPassword()))
                .build();

        String requestedRole = (request.getRole() != null) ? request.getRole().toUpperCase() : "CUSTOMER";

        if ("ADMIN".equals(requestedRole)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Error: Cannot register as ADMIN.");
        }

        var role = roleRepository.findByName(requestedRole)
                .orElseGet(() -> roleRepository.findByName("CUSTOMER").get());

        user.addRole(role);
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .filter(u -> encoder.matches(request.getPassword(), u.getPassword()))
                .orElse(null);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        var authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toUpperCase()))
                .toList();

        var userDetails = new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );

        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(Map.of(
                "token", token,
                "type", "Bearer",
                "message", "Login successful"
        ));
    }
}