package com.example.blps.module.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponseDTO implements Serializable, Comparable<CompanyResponseDTO> {

	@NotBlank(message = "Название организации не может быть пустым")
	private String orgName;

	@Size(min = 10, max = 10, message = "ИНН должен содержать 10 цифр")
	private String inn;

	@Size(min = 13, max = 13, message = "ОГРН должен содержать 13 цифр")
	private String ogrn;

	@NotNull(message = "Номер телефона не может быть пустым")
	@Pattern(regexp = "(^$|[0-9]{11})", message = "Номер телефона должен быть введен корректно!")
	private String phone;

	private String website;

	private String description;

	private String belief;

	@Override
	public int compareTo(CompanyResponseDTO companyResponseDTO) {
		return orgName.compareTo(companyResponseDTO.getOrgName());
	}
}
