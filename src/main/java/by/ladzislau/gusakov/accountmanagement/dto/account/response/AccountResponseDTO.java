package by.ladzislau.gusakov.accountmanagement.dto.account.response;

import java.math.BigDecimal;

public record AccountResponseDTO (
        Long id,
        String iban,
        BigDecimal balance,
        boolean blocked,
        Long userId
) {}
