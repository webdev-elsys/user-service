package org.example.usermicroservice.service;

import org.example.usermicroservice.dto.request.SigninRequest;
import org.example.usermicroservice.dto.request.SignupRequest;
import org.example.usermicroservice.entity.User;

public interface UserService {
    User signup(SignupRequest signupRequest);
    User signin(SigninRequest signinRequest);
    String getEmail(String uuid);
}
