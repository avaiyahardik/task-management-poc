package com.management.task.security.model;


import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class RefreshTokenRequest {

    @NotBlank(message = "Refresh token is required")
    private String refreshToken;

}
