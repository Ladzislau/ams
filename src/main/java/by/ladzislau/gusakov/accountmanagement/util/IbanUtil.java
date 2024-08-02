package by.ladzislau.gusakov.accountmanagement.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public class IbanUtil {

    private static final String COUNTRY_CODE = "BY";
    private static final int BANK_CODE = 7771;
    private static final int ACCOUNT_NUMBER_LENGTH = 16;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomIban() {
        String accountNumber = generateRandomNumber();
        String basicIban = COUNTRY_CODE + BANK_CODE + accountNumber;
        return addCheckDigits(basicIban);
    }

    private static String generateRandomNumber() {
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < IbanUtil.ACCOUNT_NUMBER_LENGTH; i++) {
            number.append(RANDOM.nextInt(10));
        }
        return number.toString();
    }

    private static String addCheckDigits(String iban) {
        if (iban.length() < 4) {
            throw new IllegalArgumentException("IBAN is too short");
        }
        String ibanWithoutCheckDigits = iban.substring(0, 2) + "00" + iban.substring(4);
        String numericIban = convertToNumericIban(ibanWithoutCheckDigits);

        BigInteger numericIbanValue = new BigInteger(numericIban);
        BigInteger modulus = BigInteger.valueOf(97);
        BigInteger remainder = numericIbanValue.mod(modulus);
        BigInteger checkDigitsValue = modulus.subtract(remainder);

        String checkDigits = String.format("%02d", checkDigitsValue);

        return iban.substring(0, 2) + checkDigits + iban.substring(2);
    }

    private static String convertToNumericIban(String iban) {
        StringBuilder numericIban = new StringBuilder();
        for (char ch : iban.toCharArray()) {
            if (Character.isLetter(ch)) {
                numericIban.append(Character.toUpperCase(ch) - 'A' + 10);
            } else {
                numericIban.append(ch);
            }
        }
        return numericIban.toString();
    }
}
