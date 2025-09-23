package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.ConstructionDomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreatedAtTest {
    @Test
    void createdAtOk() throws ConstructionDomainException {
        // Arrange
        String createdAt = "2025-03-03";

        // Act
        CreatedAt createdAt1 = new CreatedAt(createdAt);

        // Assert
        assertEquals(createdAt, createdAt1.getCreatedAt().toString());
    }

    @Test
    void createdAtBadFormat(){
        // Arrange
        String createdAt = "45/45/4545";

        // Act and Assert
        assertThrows(ConstructionDomainException.class, () -> {
            new CreatedAt(createdAt);
        });
    }

}