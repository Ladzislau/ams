package by.ladzislau.gusakov.accountmanagement.service.account;

import by.ladzislau.gusakov.accountmanagement.dto.account.response.AccountResponseDTO;
import by.ladzislau.gusakov.accountmanagement.dto.transaction.request.TransactionRequestDTO;
import by.ladzislau.gusakov.accountmanagement.dto.transaction.response.TransactionResponseDTO;
import by.ladzislau.gusakov.accountmanagement.error.IllegalOperationException;
import by.ladzislau.gusakov.accountmanagement.error.NotFoundException;
import by.ladzislau.gusakov.accountmanagement.mapper.AccountMapper;
import by.ladzislau.gusakov.accountmanagement.model.Account;
import by.ladzislau.gusakov.accountmanagement.model.User;
import by.ladzislau.gusakov.accountmanagement.repository.AccountRepository;
import by.ladzislau.gusakov.accountmanagement.repository.UserRepository;
import by.ladzislau.gusakov.accountmanagement.util.IbanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Transactional
    @Override
    public AccountResponseDTO openAccount(UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("Invalid authentication data"));

        Account account = createAndSaveAccountForUser(user);
        return accountMapper.toAccountResponseDTO(account);
    }

    @Transactional
    @Override
    public Account openAccount(User user) {
        boolean hasRoleUser = user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_USER"));
        if (!hasRoleUser) {
            throw new IllegalOperationException("User has no privilege to open personal account");
        }

        return createAndSaveAccountForUser(user);
    }

    @Transactional
    @Override
    public TransactionResponseDTO depositFunds(UserDetails userDetails, TransactionRequestDTO transactionRequestDTO) {
        Account account = accountRepository.findByUserEmail(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("Invalid account id or authentication data"));

        if (account.isBlocked()) {
            throw new IllegalOperationException("Unable to deposit funds into the account. " +
                    "The account is currently blocked.");
        }

        BigDecimal updatedBalance = account.getBalance().add(transactionRequestDTO.amount());
        account.setBalance(updatedBalance);
        accountRepository.save(account);

        return accountMapper.toTransactionResponseDTO(account, transactionRequestDTO.amount());
    }

    @Transactional
    @Override
    public TransactionResponseDTO withdrawFunds(UserDetails userDetails, TransactionRequestDTO transactionRequestDTO) {
        Account account = accountRepository.findByUserEmail(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("Invalid account id or authentication data"));

        if (account.isBlocked()) {
            throw new IllegalOperationException("Unable to withdraw funds from the account. " +
                    "The account is currently blocked.");
        }

        BigDecimal updatedBalance = account.getBalance().subtract(transactionRequestDTO.amount());
        if (updatedBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalOperationException("Insufficient funds for the withdrawal amount. " +
                    "Account balance: " + account.getBalance());
        }
        account.setBalance(updatedBalance);
        accountRepository.save(account);

        return accountMapper.toTransactionResponseDTO(account, transactionRequestDTO.amount().negate());
    }

    @Override
    public AccountResponseDTO getUserAccount(UserDetails userDetails) {
        User user = userRepository.findByEmailWithAccount(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("Invalid authentication data"));

        Account account = user.getAccount();
        if (account == null) {
            throw new NotFoundException("The requested account does not exist for the current user");
        }

        return accountMapper.toAccountResponseDTO(account);
    }

    private Account createAndSaveAccountForUser(User user) {
        if (user.getAccount() != null) {
            throw new IllegalOperationException("Account already exists");
        }

        String iban = IbanUtil.generateRandomIban();
        while (!isIbanUnique(iban)) {
            iban = IbanUtil.generateRandomIban();
        }

        Account account = new Account(iban, BigDecimal.ZERO, false, user);
        accountRepository.save(account);
        return account;
    }

    private boolean isIbanUnique(String iban) {
        return accountRepository.findByIban(iban).isEmpty();
    }
}
