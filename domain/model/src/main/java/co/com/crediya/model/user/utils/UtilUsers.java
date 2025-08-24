package co.com.crediya.model.user.utils;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import static co.com.crediya.model.user.utils.Constantes.BASE_SALARY_OUT_RANGE;
import static co.com.crediya.model.user.utils.Constantes.EMAIL_BAD_FORMAT;

public class UtilUsers {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final BigDecimal MIN_SALARY =  BigDecimal.valueOf(0.0);
    private static final BigDecimal MAX_SALARY =  BigDecimal.valueOf(15000000.0);

    private UtilUsers() {}

    public static void verifyEmailFormat(String email){
         if (!EMAIL_PATTERN.matcher(email).matches()){
             throw new IllegalArgumentException(EMAIL_BAD_FORMAT);
         }
    }

    public static void validateRangeSalary(BigDecimal baseSalaryUser) {
        if (baseSalaryUser.compareTo(MIN_SALARY) < 0 || baseSalaryUser.compareTo(MAX_SALARY) > 0){
            throw new IllegalArgumentException(BASE_SALARY_OUT_RANGE);
        }
    }
}
