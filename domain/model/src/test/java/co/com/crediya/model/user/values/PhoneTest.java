package co.com.crediya.model.user.values;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneTest {
    // El telefono es correcto
    @Test
    void phoneOk(){
        // Arrange
        Long phoneInput = 1112L;

        // Act
        Phone phone = new Phone(phoneInput);

        // Assert
        assertEquals(phoneInput, phone.getPhoneUser());
    }

    // El telefono es null
    @Test
    void phoneNull(){
        // Arrange
        Long phoneInput = null;

        // Act and Assert
        assertThrows(NullPointerException.class, () -> {
            new Phone(phoneInput);
        });
    }
}