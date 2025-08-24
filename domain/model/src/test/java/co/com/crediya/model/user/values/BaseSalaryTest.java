package co.com.crediya.model.user.values;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseSalaryTest {
    // El salario base es correcto
    @Test
    void baseSalaryOk(){
        // Arrange
        Long baseSalaryInput = 1112L;

        // Act
        BaseSalary baseSalary = new BaseSalary(baseSalaryInput);

        // Assert
        assertEquals(baseSalaryInput, baseSalary.getBaseSalaryUser());
    }

    // El salario base es null
    @Test
    void baseSalaryNull(){
        // Arrange
        Long baseSalaryInput = null;

        // Act and Assert
        assertThrows(NullPointerException.class, () -> {
            new BaseSalary(baseSalaryInput);
        });
    }
}