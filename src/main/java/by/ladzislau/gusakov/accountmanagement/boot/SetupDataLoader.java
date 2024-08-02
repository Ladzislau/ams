package by.ladzislau.gusakov.accountmanagement.boot;

import by.ladzislau.gusakov.accountmanagement.model.Account;
import by.ladzislau.gusakov.accountmanagement.model.Role;
import by.ladzislau.gusakov.accountmanagement.model.User;
import by.ladzislau.gusakov.accountmanagement.repository.RoleRepository;
import by.ladzislau.gusakov.accountmanagement.repository.UserRepository;
import by.ladzislau.gusakov.accountmanagement.service.account.AccountService;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;

    @Override
    @Transactional
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        if (alreadySetup)
            return;

        Role adminRole = createRoleIfNotFound("ROLE_ADMIN");
        Role userRole = createRoleIfNotFound("ROLE_USER");

        User admin = new User();
        admin.setFirstName("MainAdmin");
        admin.setLastName("MainAdmin");
        admin.setPassword(passwordEncoder.encode("heyHES"));
        admin.setEmail("superadmin@ladzislau-bank.by");
        admin.getRoles().add(adminRole);

        User user1 = new User();
        user1.setFirstName("John");
        user1.setLastName("Doe");
        user1.setPassword(passwordEncoder.encode("blackTesla"));
        user1.setEmail("johndoe@mail.by");
        user1.getRoles().add(userRole);
        Account user1Account = accountService.openAccount(user1);
        user1.setAccount(user1Account);

        User user2 = new User();
        user2.setFirstName("Gustavo");
        user2.setLastName("White");
        user2.setPassword(passwordEncoder.encode("losPollos"));
        user2.setEmail("gustavo@mail.by");
        user2.getRoles().add(userRole);
        Account user2Account = accountService.openAccount(user2);
        user2.setAccount(user2Account);

        userRepository.saveAll(Arrays.asList(admin, user1, user2));

        alreadySetup = true;
    }

    @Transactional
    Role createRoleIfNotFound(String name) {
        return roleRepository.findByName(name)
                .orElseGet(() -> {
                    Role role = new Role(name);
                    roleRepository.save(role);
                    return role;
                });
    }
}
