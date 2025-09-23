package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.ConstructionDomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {
    @Test
    void passwordOk() throws ConstructionDomainException {
        // Arrange
        String passeordInput = "password";

        // Act
        Password password = new Password(passeordInput);

        // Assert
        assertEquals(passeordInput, password.getPassword());
    }

    @Test
    void passwordNull(){
        // Arrange
        String passwordInput = null;

        // Act and Assert
        assertThrows(ConstructionDomainException.class, () -> {
            new Password(passwordInput);
        });
    }

    @Test
    void passwordEmpty(){
        // Arrange
        String password = "";

        // Act and Assert
        assertThrows(ConstructionDomainException.class, () -> {
            new Password(password);
        });
    }

}