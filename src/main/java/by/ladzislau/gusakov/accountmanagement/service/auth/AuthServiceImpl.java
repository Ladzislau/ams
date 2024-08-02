package by.ladzislau.gusakov.accountmanagement.service.auth;

import by.ladzislau.gusakov.accountmanagement.dto.auth.request.LoginRequestDTO;
import by.ladzislau.gusakov.accountmanagement.dto.auth.request.RegistrationRequestDTO;
import by.ladzislau.gusakov.accountmanagement.dto.auth.response.AuthResponseDTO;
import by.ladzislau.gusakov.accountmanagement.error.AuthenticationException;
import by.ladzislau.gusakov.accountmanagement.error.DuplicateException;
import by.ladzislau.gusakov.accountmanagement.mapper.UserMapper;
import by.ladzislau.gusakov.accountmanagement.model.Role;
import by.ladzislau.gusakov.accountmanagement.model.User;
import by.ladzislau.gusakov.accountmanagement.repository.RoleRepository;
import by.ladzislau.gusakov.accountmanagement.repository.UserRepository;
import by.ladzislau.gusakov.accountmanagement.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserMapper userMapper;
    private final AuthenticationProvider authProvider;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    @Override
    public AuthResponseDTO register(RegistrationRequestDTO registrationRequestDTO) {
        User user = userMapper.toUser(registrationRequestDTO);
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER does not exist"));
        String email = user.getEmail();

        boolean isEmailUnique = userRepository.findByEmail(email).isEmpty();
        if (!isEmailUnique) {
            throw new DuplicateException("User with email " + email + " already exists");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRoles(Collections.singleton(userRole));

        userRepository.save(user);
        String token = jwtUtil.generateToken(email);
        return new AuthResponseDTO(token);
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
        User user = userMapper.toUser(loginRequestDTO);

        var authInputToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

        try {
            authProvider.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return new AuthResponseDTO(token);
    }

}
