package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.ConstructionDomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BirthdayTest {
    // La fecha de nacimiento es correcta
    @Test
    void birthdateOk() throws ConstructionDomainException {
        // Arrange
        String birthdateInput = "02-03-2025";

        // Act
        Birthday birthday = new Birthday(birthdateInput);

        // Assert
        assertEquals(birthdateInput, birthday.getUserBirthday().toString());
    }

    // La fecha de nacimiento es null
    @Test
    void birthdayNull(){
        // Arrange
        String birthdayInput = null;

        // Act and Assert
        assertThrows(ConstructionDomainException.class, () -> {
            new Birthday(birthdayInput);
        });
    }

    // La fecha de nacimiento no tiene formato correcto
    @Test
    void birthdateBadFormat(){
        // Arrange
        String birthdayInput = "45/45/4545";

        // Act and Assert
        assertThrows(ConstructionDomainException.class, () -> {
            new Birthday(birthdayInput);
        });
    }
}