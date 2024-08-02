package by.ladzislau.gusakov.accountmanagement.mapper;

import by.ladzislau.gusakov.accountmanagement.dto.account.response.AccountResponseDTO;
import by.ladzislau.gusakov.accountmanagement.dto.transaction.response.TransactionResponseDTO;
import by.ladzislau.gusakov.accountmanagement.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "balance", source = "account.balance", qualifiedByName = "formatBalance")
    AccountResponseDTO toAccountResponseDTO(Account account);

    @Mapping(target = "accountId", source = "account.id")
    @Mapping(target = "userId", source = "account.user.id")
    @Mapping(target = "updatedBalance", source = "account.balance", qualifiedByName = "formatBalance")
    @Mapping(target = "transactionAmount", source = "amount", qualifiedByName = "formatTransactionAmount")
    TransactionResponseDTO toTransactionResponseDTO(Account account, BigDecimal amount);

    @Named("formatTransactionAmount")
    default String formatTransactionAmount(BigDecimal amount) {
        if (amount == null) {
            return "";
        }
        return (amount.compareTo(BigDecimal.ZERO) >= 0 ? "+" : "-") +
                amount.abs().setScale(3, RoundingMode.HALF_UP);
    }

    @Named("formatBalance")
    default String formatBalance(BigDecimal balance) {
        if (balance == null) {
            return "";
        }
        return balance.setScale(3, RoundingMode.HALF_UP).toString();
    }
}
