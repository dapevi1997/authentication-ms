package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.ConstructionDomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {
    @Test
    void passeordOk() throws ConstructionDomainException {
        // Arrange
        String passeordInput = "password";

        // Act
        Password password = new Password(passeordInput);

        // Assert
        assertEquals(passeordInput, password.getPassword());
    }

    @Test
    void nameNull(){
        // Arrange
        String passwordInput = null;

        // Act and Assert
        assertThrows(ConstructionDomainException.class, () -> {
            new Name(passwordInput);
        });
    }

    @Test
    void nameEmpty(){
        // Arrange
        String password = "";

        // Act and Assert
        assertThrows(ConstructionDomainException.class, () -> {
            new Name(password);
        });
    }

}