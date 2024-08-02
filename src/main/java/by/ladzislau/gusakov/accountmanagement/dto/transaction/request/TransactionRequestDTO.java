package by.ladzislau.gusakov.accountmanagement.dto.transaction.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransactionRequestDTO(
        @NotNull
        @Positive
        BigDecimal amount
) {}
