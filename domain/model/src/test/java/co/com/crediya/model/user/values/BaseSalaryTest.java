package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.UserContructionException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BaseSalaryTest {
    // El salario base es correcto
    @Test
    void baseSalaryOk() throws UserContructionException {
        // Arrange
        String baseSalaryInput = "1233.25";

        // Act
        BaseSalary baseSalary = new BaseSalary(baseSalaryInput);

        // Assert
        assertEquals(new BigDecimal(baseSalaryInput), baseSalary.getBaseSalaryUser());
    }

    // El salario base es null
    @Test
    void baseSalaryNull(){
        // Arrange
        String baseSalaryInput = null;

        // Act and Assert
        assertThrows(UserContructionException.class, () -> {
            new BaseSalary(baseSalaryInput);
        });
    }

    // El salario base es menor que 0
    @Test
    void baseSalaryNegative(){
        // Arrange
        String baseSalaryInput = "-15000.0";

        // Act and Assert
        assertThrows(UserContructionException.class, () -> {
            new BaseSalary(baseSalaryInput);
        });
    }

    // El salario base es mayor que $15.000.000
    @Test
    void baseSalaryOut(){
        // Arrange
        String baseSalaryInput = "16000000.0";

        // Act and Assert
        assertThrows(UserContructionException.class, () -> {
            new BaseSalary(baseSalaryInput);
        });
    }

    // El salario base no es un valor numérico
    @Test
    void baseSalaryNotNumeric(){
        // Arrange
        String baseSalaryInput = "160000df00.0";

        // Act and Assert
        assertThrows(UserContructionException.class, () -> {
            new BaseSalary(baseSalaryInput);
        });
    }
}