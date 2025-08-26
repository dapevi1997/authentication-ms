package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.ConstructionDomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdTest {
    // El id del rol es correcto
    @Test
    void roleOk() throws ConstructionDomainException {
        // Arrange
        String roleInput = "1";

        // Act
        IdRole idRole = new IdRole(roleInput);

        // Assert
        assertEquals(Long.valueOf(roleInput), idRole.getIdRole());
    }

    // El telefono es null
    @Test
    void roleNull(){
        // Arrange
        String roleInput = null;

        // Act and Assert
        assertThrows(ConstructionDomainException.class, () -> {
            new IdRole(roleInput);
        });
    }

    // El telefono no es un valor numérico
    @Test
    void roleNotNumeric(){
        // Arrange
        String roleInput = "12ds";

        // Act and Assert
        assertThrows(ConstructionDomainException.class, () -> {
            new IdRole(roleInput);
        });
    }
}