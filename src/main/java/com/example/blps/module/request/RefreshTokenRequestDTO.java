package com.example.blps.module.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RefreshTokenRequestDTO {

	@NotBlank
	private String refreshToken;
}
