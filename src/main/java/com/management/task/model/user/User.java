package com.management.task.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.management.task.type.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Entity
@Table(name = "user", schema = "public")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_id")
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "u_created_at", updatable = false)
    private OffsetDateTime created = OffsetDateTime.now();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "u_modified_at")
    private OffsetDateTime modified = OffsetDateTime.now();

    @NotBlank
    @Size(max = 30, message = "Firstname {jakarta.validation.constraints.Size.message}")
    @Column(name = "u_firstname", length = 30)
    private String firstname;

    @Size(max = 30, message = "Lastname {jakarta.validation.constraints.Size.message}")
    @Column(name = "u_lastname", length = 30)
    private String lastname;

    @NotBlank(message = "Email is required")
    @Size(max = 128)
    @Email(message = "Well-formed email address is required")
    @Column(unique = true, name = "u_email", length = 128)
    private String email;

    @Column(name = "u_password", length = 128)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull(message = "Role is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "u_role", length = 20)
    private Role role;
}
