package com.management.task.security.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignInRequest {
    @NotBlank(message = "Email is required")
    @Size(max = 128, message = "Email {jakarta.validation.constraints.Size.message}")
    @Email(message = "Well-formed email address is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(max = 30, message = "Password {jakarta.validation.constraints.Size.message}")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}
