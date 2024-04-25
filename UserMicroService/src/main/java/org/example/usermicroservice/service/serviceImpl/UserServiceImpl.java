package org.example.usermicroservice.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.example.usermicroservice.dto.request.SigninRequest;
import org.example.usermicroservice.dto.request.SignupRequest;
import org.example.usermicroservice.entity.User;
import org.example.usermicroservice.repository.UserRepository;
import org.example.usermicroservice.service.UserService;
import org.example.usermicroservice.shared.exception.InvalidCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static org.example.usermicroservice.mapper.UserMapper.USER_MAPPER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User signup(SignupRequest signupRequest) {
        User user = USER_MAPPER.fromSignupRequest(signupRequest);
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public User signin(SigninRequest signinRequest) {
        User user = userRepository.findByEmail(signinRequest.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid email"));

        if (!passwordEncoder.matches(signinRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        return user;
    }

    @Override
    public String getEmail(String uuid) {
        return userRepository.findByUuid(uuid).map(User::getEmail).orElse(null);
    }
}
