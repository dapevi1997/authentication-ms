package co.com.crediya.model.user.utils;

import co.com.crediya.model.user.exception.UserContructionException;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.regex.Pattern;

import static co.com.crediya.model.user.utils.Constantes.*;

public class UtilUsers {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final BigDecimal MIN_SALARY =  BigDecimal.valueOf(0.0);
    private static final BigDecimal MAX_SALARY =  BigDecimal.valueOf(15000000.0);

    private UtilUsers() {}

    public static void verifyEmailFormat(String email) throws UserContructionException {
         if (!EMAIL_PATTERN.matcher(email).matches()){
             throw new UserContructionException(EMAIL_BAD_FORMAT);
         }
    }

    public static BigDecimal validateBaseSalary(String baseSalaryUser) throws UserContructionException {
        try {
            BigDecimal baseSalaryBigDecimal = new BigDecimal(baseSalaryUser);
            if (baseSalaryBigDecimal.compareTo(MIN_SALARY) < 0 || baseSalaryBigDecimal.compareTo(MAX_SALARY) > 0){
                throw new UserContructionException(BASE_SALARY_OUT_RANGE);
            }
            return baseSalaryBigDecimal;
        } catch (NumberFormatException ex){
            throw new UserContructionException(BASE_SALARY_NOT_NUMBER);
        }
    }

    public static Long validateToLong(String documentoIdentidadUsuario) throws UserContructionException {
        try {
            return Long.valueOf(documentoIdentidadUsuario);
        } catch (NumberFormatException ex){
            throw new UserContructionException(DOCUMENT_ID_NOT_NUMBER);
        }
    }

    public static void validateNotNull(String baseSalaryUser, String message) throws UserContructionException {
        if (Objects.isNull(baseSalaryUser)){
            throw new UserContructionException(message);
        }
    }
}
