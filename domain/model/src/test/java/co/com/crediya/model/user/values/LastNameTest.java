package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.UserContructionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LastNameTest {
    // El apellido es correcto
    @Test
    void lastNameOk() throws UserContructionException {
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
        assertThrows(UserContructionException.class, () -> {
            new LastName(lastNameInput);
        });
    }

    // El apellido es vacío
    @Test
    void lastNameEmpty(){
        // Arrange
        String lastNameInput = "";

        // Act and Assert
        assertThrows(UserContructionException.class, () -> {
            new LastName(lastNameInput);
        });
    }
}