package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.ConstructionDomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LastNameTest {
    // El apellido es correcto
    @Test
    void lastNameOk() throws ConstructionDomainException {
        // Arrange
        String lastNameInput = "Apellido";

        // Act
        LastName lastName = new LastName(lastNameInput);

        // Assert
        assertEquals(lastNameInput, lastName.getLastNameUser());
    }

    // El apellido es null
    @Test
    void lastNameNull(){
        // Arrange
        String lastNameInput = null;

        // Act and Assert
        assertThrows(ConstructionDomainException.class, () -> {
            new LastName(lastNameInput);
        });
    }

    // El apellido es vacío
    @Test
    void lastNameEmpty(){
        // Arrange
        String lastNameInput = "";

        // Act and Assert
        assertThrows(ConstructionDomainException.class, () -> {
            new LastName(lastNameInput);
        });
    }
}