package by.ladzislau.gusakov.accountmanagement.service.account;

import by.ladzislau.gusakov.accountmanagement.dto.account.response.AccountResponseDTO;
import by.ladzislau.gusakov.accountmanagement.dto.transaction.request.TransactionRequestDTO;
import by.ladzislau.gusakov.accountmanagement.dto.transaction.response.TransactionResponseDTO;
import by.ladzislau.gusakov.accountmanagement.model.Account;
import by.ladzislau.gusakov.accountmanagement.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;

public interface AccountService {

    Account openAccount(User user);

    @PreAuthorize("hasRole('USER')")
    AccountResponseDTO openAccount(UserDetails userDetails);

    @PreAuthorize("hasRole('USER')")
    TransactionResponseDTO depositFunds(UserDetails userDetails, TransactionRequestDTO transactionRequestDTO);

    @PreAuthorize("hasRole('USER')")
    TransactionResponseDTO withdrawFunds(UserDetails userDetails, TransactionRequestDTO transactionRequestDTO);

    @PreAuthorize("hasRole('USER')")
    AccountResponseDTO getUserAccount(UserDetails userDetails);
}
