package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.UserContructionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneTest {
    // El telefono es correcto
    @Test
    void phoneOk() throws UserContructionException {
        // Arrange
        String phoneInput = "123";

        // Act
        Phone phone = new Phone(phoneInput);

        // Assert
        assertEquals(Long.valueOf(phoneInput), phone.getPhoneUser());
    }

    // El telefono es null
    @Test
    void phoneNull(){
        // Arrange
        String phoneInput = null;

        // Act and Assert
        assertThrows(UserContructionException.class, () -> {
            new Phone(phoneInput);
        });
    }

    // El telefono no es un valor numérico
    @Test
    void phoneNotNumeric(){
        // Arrange
        String phoneInput = "12ds";

        // Act and Assert
        assertThrows(UserContructionException.class, () -> {
            new Phone(phoneInput);
        });
    }
}