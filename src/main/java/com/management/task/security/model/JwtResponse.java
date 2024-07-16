package com.management.task.security.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class JwtResponse {
	private Long id;
	private String firstname;
	private String lastname;
	private String email;
	private Set<String> roles;
	private String tokenType;
	private String accessToken;
	private String refreshToken;
}
