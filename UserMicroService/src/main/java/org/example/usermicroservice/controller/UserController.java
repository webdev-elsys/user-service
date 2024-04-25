package org.example.usermicroservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.usermicroservice.dto.request.SigninRequest;
import org.example.usermicroservice.dto.request.SignupRequest;
import org.example.usermicroservice.entity.User;
import org.example.usermicroservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    private ResponseEntity<User> signup(@Valid @RequestBody SignupRequest signupRequest) {
        return ResponseEntity.ok(userService.signup(signupRequest));
    }

    @PostMapping("/signin")
    private ResponseEntity<User> signin(@Valid @RequestBody SigninRequest signinRequest) {
        return ResponseEntity.ok(userService.signin(signinRequest));
    }

    @GetMapping("/{uuid}/email")
    private ResponseEntity<String> getEmail(@PathVariable String uuid) {
        return ResponseEntity.ok(userService.getEmail(uuid));
    }
}
