package org.example.usermicroservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignupRequest {
    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
