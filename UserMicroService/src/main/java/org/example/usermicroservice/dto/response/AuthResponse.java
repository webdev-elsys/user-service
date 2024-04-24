package org.example.usermicroservice.dto.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthResponse {
    @NotEmpty
    private String accessToken;

    @NotEmpty
    private String refreshToken;
}
