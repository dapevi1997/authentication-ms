package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.ConstructionDomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {
    @Test
    void passwordOk() throws ConstructionDomainException {
        // Arrange
        String passwordInput = "Password";

        // Act
        Password password = new Password(passwordInput);

        // Assert
        assertEquals(passwordInput, password.getPassword());
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
        String passwordInput = "";

        // Act and Assert
        assertThrows(ConstructionDomainException.class, () -> {
            new Name(passwordInput);
        });
    }

}