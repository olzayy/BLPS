package com.example.blps.module.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Data
public class SignUpRequestDTO {
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
