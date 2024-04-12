package org.example.usermicroservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SigninRequest {
    @NotEmpty
    @Email
    private String email;

    // TODO: Add validation for password
    @NotEmpty
    private String password;
}

