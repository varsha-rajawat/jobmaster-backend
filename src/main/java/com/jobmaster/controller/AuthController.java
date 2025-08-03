package com.jobmaster.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobmaster.dto.AuthResponseDTO;
import com.jobmaster.dto.AuthenticationDTO;
import com.jobmaster.dto.SignUpDTO;
import com.jobmaster.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpDTO signUpDTO) {
        if (authService.hasUserWithEmail(signUpDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
        return ResponseEntity.ok(authService.createUser(signUpDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody AuthenticationDTO authDTO) {
        return ResponseEntity.ok(authService.getAuthResponseDTO(authDTO));
    }
}
