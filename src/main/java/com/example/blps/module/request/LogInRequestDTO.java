package com.example.blps.module.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LogInRequestDTO {

	@NotBlank
	private String email;

	@NotBlank
	private String password;
}
