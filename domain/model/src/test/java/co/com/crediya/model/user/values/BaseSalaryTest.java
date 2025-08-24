package co.com.crediya.model.user.values;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BaseSalaryTest {
    // El salario base es correcto
    @Test
    void baseSalaryOk(){
        // Arrange
        BigDecimal baseSalaryInput = BigDecimal.valueOf(15000000.0);

        // Act
        BaseSalary baseSalary = new BaseSalary(baseSalaryInput);

        // Assert
        assertEquals(baseSalaryInput, baseSalary.getBaseSalaryUser());
    }

    // El salario base es null
    @Test
    void baseSalaryNull(){
        // Arrange
        BigDecimal baseSalaryInput = null;

        // Act and Assert
        assertThrows(NullPointerException.class, () -> {
            new BaseSalary(baseSalaryInput);
        });
    }

    // El salario base es menor que 0
    @Test
    void baseSalaryNegative(){
        // Arrange
        BigDecimal baseSalaryInput = BigDecimal.valueOf(-15000.0);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new BaseSalary(baseSalaryInput);
        });
    }

    // El salario base es mayor que $15.000.000
    @Test
    void baseSalaryOut(){
        // Arrange
        BigDecimal baseSalaryInput = BigDecimal.valueOf(16000000.0);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new BaseSalary(baseSalaryInput);
        });
    }
}