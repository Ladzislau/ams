package by.ladzislau.gusakov.accountmanagement.controller;

import by.ladzislau.gusakov.accountmanagement.dto.account.response.AccountResponseDTO;
import by.ladzislau.gusakov.accountmanagement.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/admin/accounts")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public PagedModel<AccountResponseDTO> getAccountsPage(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return adminService.getAccountsPage(pageable);
    }

    @PatchMapping("/{accountId}/block")
    public AccountResponseDTO blockAccount(@PathVariable Long accountId) {
        return adminService.block(accountId);
    }

    @PatchMapping("/{accountId}/unblock")
    public AccountResponseDTO unblockAccount(@PathVariable Long accountId) {
        return adminService.unblock(accountId);
    }
}
