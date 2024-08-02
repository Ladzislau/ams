package by.ladzislau.gusakov.accountmanagement.dto.auth.request;

import jakarta.validation.constraints.*;

public record RegistrationRequestDTO(

        @NotNull
        @Email
        String email,

        @NotBlank
        @Size(max = 30)
        String firstName,

        @NotBlank
        @Size(max = 30)
        String lastName,

        @NotBlank
        @Size(min = 6, max = 30)
        String password
) {}
