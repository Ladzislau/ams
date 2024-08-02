package by.ladzislau.gusakov.accountmanagement.dto.error;

import java.util.Date;

public record ErrorResponse (
        String message,
        int status,
        Date timestamp
) {}
