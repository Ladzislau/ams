package by.ladzislau.gusakov.accountmanagement.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(

        @Email
        String email,

        @NotBlank
        String password
) {}
