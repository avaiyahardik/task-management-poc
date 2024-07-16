package com.management.task.security.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RefreshTokenResponse {
	private String tokenType;
	private String accessToken;
	private String refreshToken;
}
