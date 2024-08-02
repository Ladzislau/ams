package by.ladzislau.gusakov.accountmanagement.service.auth;

import by.ladzislau.gusakov.accountmanagement.dto.auth.request.LoginRequestDTO;
import by.ladzislau.gusakov.accountmanagement.dto.auth.request.RegistrationRequestDTO;
import by.ladzislau.gusakov.accountmanagement.dto.auth.response.AuthResponseDTO;

public interface AuthService {

    AuthResponseDTO register(RegistrationRequestDTO registrationRequestDTO);

    AuthResponseDTO login(LoginRequestDTO loginRequestDTO);
}
