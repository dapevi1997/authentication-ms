package co.com.crediya.model.user.values;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {
    // El email es correcto
    @Test
    void emailOk(){
        // Arrange
        String emailInput = "usuario@mail.com";

        // Act
        Email email = new Email(emailInput);

        // Assert
        assertEquals(emailInput, email.getEmailUser());
    }

    // El email es null
    @Test
    void emailNull(){
        // Arrange
        String emailInput = null;

        // Act and Assert
        assertThrows(NullPointerException.class, () -> {
            new Email(emailInput);
        });
    }

    // El email no tiene el formato correcto
    @Test
    void emailEmpty(){
        // Arrange
        String emailInput = "mal_email.com";

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Email(emailInput);
        });
    }

}