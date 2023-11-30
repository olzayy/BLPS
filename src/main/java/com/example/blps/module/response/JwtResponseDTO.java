package com.example.blps.module.response;

import lombok.Data;

@Data
public class JwtResponseDTO {

	private String token;
	private String type = "Bearer";
	private String refreshToken;
	private Long id;
	private String username;

	public JwtResponseDTO(String accessToken, String refreshToken, Long id, String username) {
		this.token = accessToken;
		this.refreshToken = refreshToken;
		this.id = id;
		this.username = username;
	}
}
