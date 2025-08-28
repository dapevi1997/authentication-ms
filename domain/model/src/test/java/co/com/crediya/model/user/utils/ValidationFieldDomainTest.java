package co.com.crediya.model.user.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import co.com.crediya.model.user.exception.ConstructionDomainException;

class ValidationFieldDomainTest {
    @Test
    void verifyEmailFormatOk() throws ConstructionDomainException {
        ValidationFieldDomain.verifyEmailFormat("test@mail.com");
    }

    @Test
    void verifyEmailFormatFail() {
        assertThrows(ConstructionDomainException.class,
                () -> ValidationFieldDomain.verifyEmailFormat("badmail.com"));
    }

    @Test
    void validateBaseSalaryOk() throws ConstructionDomainException {
        BigDecimal salary = ValidationFieldDomain.validateBaseSalary("1000.50");
        assertEquals(new BigDecimal("1000.50"), salary);
    }

    @Test
    void validateBaseSalaryNegative() {
        assertThrows(ConstructionDomainException.class,
                () -> ValidationFieldDomain.validateBaseSalary("-1"));
    }

    @Test
    void validateBaseSalaryOutOfRange() {
        assertThrows(ConstructionDomainException.class,
                () -> ValidationFieldDomain.validateBaseSalary("20000000"));
    }

    @Test
    void validateBaseSalaryNotNumber() {
        assertThrows(ConstructionDomainException.class,
                () -> ValidationFieldDomain.validateBaseSalary("abc"));
    }

    @Test
    void validateToLongOk() throws ConstructionDomainException {
        Long value = ValidationFieldDomain.validateToLong("123", "msg");
        assertEquals(123L, value);
    }

    @Test
    void validateToLongFail() {
        assertThrows(ConstructionDomainException.class,
                () -> ValidationFieldDomain.validateToLong("abc", "msg"));
    }

    @Test
    void validateNotNullOk() throws ConstructionDomainException {
        ValidationFieldDomain.validateNotNull("notnull", "msg");
    }

    @Test
    void validateNotNullFail() {
        assertThrows(ConstructionDomainException.class,
                () -> ValidationFieldDomain.validateNotNull(null, "msg"));
    }

    @Test
    void validateBirthayOk() throws ConstructionDomainException {
        LocalDate date = ValidationFieldDomain.validateBirthay("2025-08-28", "msg");
        assertEquals(LocalDate.of(2025, 8, 28), date);
    }

    @Test
    void validateBirthayFail() {
        assertThrows(ConstructionDomainException.class,
                () -> ValidationFieldDomain.validateBirthay("bad-date", "msg"));
    }

    @Test
    void validateNotEmptyOk() throws ConstructionDomainException {
        ValidationFieldDomain.validateNotEmpty("abc", "msg");
    }

    @Test
    void validateNotEmptyFail() {
        assertThrows(ConstructionDomainException.class,
                () -> ValidationFieldDomain.validateNotEmpty("", "msg"));
    }
}
