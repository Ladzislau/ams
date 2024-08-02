package by.ladzislau.gusakov.accountmanagement.service.admin;

import by.ladzislau.gusakov.accountmanagement.dto.account.response.AccountResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.security.access.prepost.PreAuthorize;

public interface AdminService {

    @PreAuthorize("hasRole('ADMIN')")
    PagedModel<AccountResponseDTO> getAccountsPage(Pageable pageable);

    @PreAuthorize("hasRole('ADMIN')")
    AccountResponseDTO block(Long accountId);

    @PreAuthorize("hasRole('ADMIN')")
    AccountResponseDTO unblock(Long accountId);
}
