package org.example.usermicroservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.usermicroservice.dto.request.SigninRequest;
import org.example.usermicroservice.dto.request.SignupRequest;
import org.example.usermicroservice.dto.response.AuthResponse;
import org.example.usermicroservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/signup")
    private ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok(userService.signup(signupRequest));
    }

    @PostMapping("/signin")
    private ResponseEntity<AuthResponse> signin(@Valid @RequestBody SigninRequest signinRequest) {
        return ResponseEntity.ok(userService.signin(signinRequest));
    }

    @PostMapping("/refresh-token")
    private void refreshToken(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException {
        userService.refreshToken(request, response);
    }

    @DeleteMapping("/signout")
    private ResponseEntity<?> signout() {
        userService.signout();
        return ResponseEntity.noContent().build();
    }
}
