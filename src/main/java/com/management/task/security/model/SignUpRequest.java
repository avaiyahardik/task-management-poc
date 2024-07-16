package com.management.task.security.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.management.task.type.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignUpRequest(

        @NotBlank(message = "Firstname is required")
        @Size(max = 25, message = "Firstname {jakarta.validation.constraints.Size.message}")
        String firstname,

        @Size(max = 25, message = "Lastname {jakarta.validation.constraints.Size.message}")
        String lastname,

        @NotBlank(message = "Email is required")
        @Size(max = 128, message = "Email {jakarta.validation.constraints.Size.message}")
        @Email(message = "Well-formed email address is required")
        String email,

        @NotBlank(message = "Password is required")
        @Size(max = 30, message = "Password {jakarta.validation.constraints.Size.message}")
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        String password,

        @NotNull(message = "Role is required")
        Role role

) {
}
