package co.com.crediya.model.user.values;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LastNameTest {
    // El apellido es correcto
    @Test
    void lastNameOk(){
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
        assertThrows(NullPointerException.class, () -> {
            new Name(lastNameInput);
        });
    }

    // El apellido es vacío
    @Test
    void lastNameEmpty(){
        // Arrange
        String lastNameInput = "";

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Name(lastNameInput);
        });
    }
}