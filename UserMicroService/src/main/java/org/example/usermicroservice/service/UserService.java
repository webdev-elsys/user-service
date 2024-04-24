package org.example.usermicroservice.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.usermicroservice.dto.request.SigninRequest;
import org.example.usermicroservice.dto.request.SignupRequest;
import org.example.usermicroservice.dto.response.AuthResponse;

import java.io.IOException;

public interface UserService {
    AuthResponse signup(SignupRequest signupRequest);
    AuthResponse signin(SigninRequest signinRequest);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
    void signout();
}
