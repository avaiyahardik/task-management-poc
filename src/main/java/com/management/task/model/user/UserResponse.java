package com.management.task.model.user;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    // Exposing roles so FE can also drive access accordingly
    private Set<String> roles;
}
