package co.com.crediya.model.user.values;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NameTest {

    // El nombre es correcto
    @Test
    void nameOk(){
        // Arrange
        String nameInput = "Nombre";

        // Act
        Name name = new Name(nameInput);

        // Assert
        assertEquals(nameInput, name.getNameUser());
    }

    // El nombre es null
    @Test
    void nameNull(){
        // Arrange
        String nameInput = null;

        // Act and Assert
        assertThrows(NullPointerException.class, () -> {
            new Name(nameInput);
        });
    }

    // El nombre es vacío
    @Test
    void nameEmpty(){
        // Arrange
        String nameInput = "";

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            new Name(nameInput);
        });
    }

}