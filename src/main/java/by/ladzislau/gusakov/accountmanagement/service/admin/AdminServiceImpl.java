package by.ladzislau.gusakov.accountmanagement.service.admin;

import by.ladzislau.gusakov.accountmanagement.dto.account.response.AccountResponseDTO;
import by.ladzislau.gusakov.accountmanagement.error.NotFoundException;
import by.ladzislau.gusakov.accountmanagement.mapper.AccountMapper;
import by.ladzislau.gusakov.accountmanagement.model.Account;
import by.ladzislau.gusakov.accountmanagement.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;

    @Transactional(readOnly = true)
    @Override
    public PagedModel<AccountResponseDTO> getAccountsPage(Pageable pageable) {
        Page<Account> accounts = accountRepository.findAll(pageable);

        List<AccountResponseDTO> mappedAccounts = accounts.getContent().stream()
                .map(accountMapper::toAccountResponseDTO)
                .toList();

        return new PagedModel<>(new PageImpl<>(mappedAccounts, pageable, accounts.getTotalElements()));
    }

    @Transactional
    @Override
    public AccountResponseDTO block(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Invalid account id: " + accountId));

        account.setBlocked(true);
        accountRepository.save(account);

        return accountMapper.toAccountResponseDTO(account);
    }

    @Transactional
    @Override
    public AccountResponseDTO unblock(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Invalid account id: " + accountId));

        account.setBlocked(false);
        accountRepository.save(account);

        return accountMapper.toAccountResponseDTO(account);
    }
}
