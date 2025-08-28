package co.com.crediya.model.user.utils;

import co.com.crediya.model.user.exception.ConstructionDomainException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.regex.Pattern;

import static co.com.crediya.model.user.utils.Constantes.*;

public class ValidationFieldDomain {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private static final BigDecimal MIN_SALARY =  BigDecimal.valueOf(0.0);
    private static final BigDecimal MAX_SALARY =  BigDecimal.valueOf(15000000.0);

    private ValidationFieldDomain() {}

    public static void verifyEmailFormat(String email) throws ConstructionDomainException {
         if (!EMAIL_PATTERN.matcher(email).matches()){
             throw new ConstructionDomainException(EMAIL_BAD_FORMAT);
         }
    }

    public static BigDecimal validateBaseSalary(String baseSalaryUser) throws ConstructionDomainException {
        try {
            BigDecimal baseSalaryBigDecimal = new BigDecimal(baseSalaryUser);
            if (baseSalaryBigDecimal.compareTo(MIN_SALARY) < 0 || baseSalaryBigDecimal.compareTo(MAX_SALARY) > 0){
                throw new ConstructionDomainException(BASE_SALARY_OUT_RANGE);
            }
            return baseSalaryBigDecimal;
        } catch (NumberFormatException ex){
            throw new ConstructionDomainException(BASE_SALARY_NOT_NUMBER);
        }
    }

    public static Long validateToLong(String documentoIdentidadUsuario, String message) throws ConstructionDomainException {
        try {
            return Long.valueOf(documentoIdentidadUsuario);
        } catch (NumberFormatException ex){
            throw new ConstructionDomainException(message);
        }
    }

    public static void validateNotNull(String baseSalaryUser, String message) throws ConstructionDomainException {
        if (Objects.isNull(baseSalaryUser)){
            throw new ConstructionDomainException(message);
        }
    }

    public static LocalDate validateBirthay(String userBirthday, String message) throws ConstructionDomainException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            validateNotNull(userBirthday, BIRTHDAY_NULL);
            return LocalDate.parse(userBirthday, formatter);
        } catch (DateTimeParseException e) {
            throw new ConstructionDomainException(message);
        }

    }

    public static void validateNotEmpty(String adress, String message) throws ConstructionDomainException {
        if (adress.isEmpty()){
            throw new ConstructionDomainException(message);
        }
    }
}
