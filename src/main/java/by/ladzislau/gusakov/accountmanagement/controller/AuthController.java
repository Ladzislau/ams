package by.ladzislau.gusakov.accountmanagement.controller;

import by.ladzislau.gusakov.accountmanagement.dto.auth.request.LoginRequestDTO;
import by.ladzislau.gusakov.accountmanagement.dto.auth.request.RegistrationRequestDTO;
import by.ladzislau.gusakov.accountmanagement.dto.auth.response.AuthResponseDTO;
import by.ladzislau.gusakov.accountmanagement.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registration")
    public AuthResponseDTO performRegistration(@RequestBody @Valid RegistrationRequestDTO registrationDTO) {
        return authService.register(registrationDTO);
    }

    @PatchMapping("/login")
    public AuthResponseDTO performLogin(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        return authService.login(loginRequestDTO);
    }
}
