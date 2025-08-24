package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.UserContructionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {
    // El email es correcto
    @Test
    void emailOk() throws UserContructionException {
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
        assertThrows(UserContructionException.class, () -> {
            new Email(emailInput);
        });
    }

    // El email no tiene el formato correcto
    @Test
    void emailEmpty(){
        // Arrange
        String emailInput = "mal_email.com";

        // Act and Assert
        assertThrows(UserContructionException.class, () -> {
            new Email(emailInput);
        });
    }

}