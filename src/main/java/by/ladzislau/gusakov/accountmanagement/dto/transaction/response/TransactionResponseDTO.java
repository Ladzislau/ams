package by.ladzislau.gusakov.accountmanagement.dto.transaction.response;

public record TransactionResponseDTO (
        Long accountId,
        String iban,
        String updatedBalance,
        Long userId,
        String transactionAmount
) {}
