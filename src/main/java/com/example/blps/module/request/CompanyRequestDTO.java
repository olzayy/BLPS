package com.example.blps.module.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class CompanyRequestDTO implements Serializable {

	@NotBlank(message = "Название организации не может быть пустым")
	private final String orgName;

	@Size(min = 10, max = 10, message = "ИНН должен содержать 10 цифр")
	private final String inn;

	@Size(min = 13, max = 13, message = "ОГРН должен содержать 13 цифр")
	private final String ogrn;

	@NotNull(message = "Номер телефона не может быть пустым")
	@Pattern(regexp = "(^$|[0-9]{11})", message = "Номер телефона должен быть введен корректно!")
	private final String phone;

	private final String website;

	private final String description;

	private final String belief;
}
