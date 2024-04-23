package org.example.usermicroservice.service.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.usermicroservice.config.JWTService;
import org.example.usermicroservice.dto.request.SigninRequest;
import org.example.usermicroservice.dto.request.SignupRequest;
import org.example.usermicroservice.dto.response.AuthResponse;
import org.example.usermicroservice.entity.User;
import org.example.usermicroservice.repository.UserRepository;
import org.example.usermicroservice.service.UserService;
import org.example.usermicroservice.shared.exception.InvalidCredentialsException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static org.example.usermicroservice.mapper.UserMapper.USER_MAPPER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse signup(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new InvalidCredentialsException("Email already exists!");
        }

        User user = USER_MAPPER.fromSignupRequest(signupRequest);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setRefreshToken(jwtService.generateRefreshToken(user));

        userRepository.save(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setAccessToken(jwtService.generateToken(user));
        authResponse.setRefreshToken(jwtService.generateRefreshToken(user));

        return authResponse;
    }

    @Override
    public AuthResponse signin(SigninRequest signinRequest) {
        User user = userRepository.findByEmail(signinRequest.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Email not found!"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signinRequest.getEmail(),
                        signinRequest.getPassword()
                )
        );

        String refreshToken = jwtService.generateRefreshToken(user);
        AuthResponse response = new AuthResponse();
        response.setAccessToken(jwtService.generateToken(user));
        response.setRefreshToken(refreshToken);
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return response;
    }

    @Override
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractEmail(refreshToken);
        if (userEmail != null) {
            User user = this.userRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);
                AuthResponse authResponse = new AuthResponse();
                authResponse.setAccessToken(accessToken);

                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    @Override
    public void signout() {
        User user = userRepository.findByEmail(
                SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getName()
        ).orElseThrow();

        user.setRefreshToken(null);
        userRepository.save(user);
    }
}
