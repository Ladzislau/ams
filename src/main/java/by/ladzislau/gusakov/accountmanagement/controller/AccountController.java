package by.ladzislau.gusakov.accountmanagement.controller;

import by.ladzislau.gusakov.accountmanagement.dto.account.response.AccountResponseDTO;
import by.ladzislau.gusakov.accountmanagement.dto.transaction.request.TransactionRequestDTO;
import by.ladzislau.gusakov.accountmanagement.dto.transaction.response.TransactionResponseDTO;
import by.ladzislau.gusakov.accountmanagement.service.account.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public AccountResponseDTO openNewAccount(@AuthenticationPrincipal UserDetails userDetails) {
        return accountService.openAccount(userDetails);
    }

    @GetMapping("/me")
    public AccountResponseDTO getUserAccount(@AuthenticationPrincipal UserDetails userDetails) {
        return accountService.getUserAccount(userDetails);
    }

    @PatchMapping("/me/deposit")
    public TransactionResponseDTO deposit(@AuthenticationPrincipal UserDetails userDetails,
                                          @RequestBody @Valid TransactionRequestDTO transactionRequestDTO) {
        return accountService.depositFunds(userDetails, transactionRequestDTO);
    }

    @PatchMapping("/me/withdraw")
    public TransactionResponseDTO withdraw(@AuthenticationPrincipal UserDetails userDetails,
                                       @RequestBody @Valid TransactionRequestDTO transactionRequestDTO) {
        return accountService.withdrawFunds(userDetails, transactionRequestDTO);
    }
}
