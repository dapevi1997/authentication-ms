package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.UserContructionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentIdTest {
    // El documento de identidad es correcto
    @Test
    void documentIdOk() throws UserContructionException {
        // Arrange
        String documentIdInput = "1234";

        // Act
        DocumentId documentId = new DocumentId(documentIdInput);

        // Assert
        assertEquals(Long.parseLong(documentIdInput), documentId.getDocumentoIdentidadUsuario());
    }

    // El documento de identidad es null
    @Test
    void documentIdNull(){
        // Arrange
        String documentIdInput = null;

        // Act and Assert
        assertThrows(UserContructionException.class, () -> {
            new DocumentId(documentIdInput);
        });
    }

    // El documento de identidad no es un valor numérico
    @Test
    void documentIdNotNumeric(){
        // Arrange
        String documentIdInput = "1s2";

        // Act and Assert
        assertThrows(UserContructionException.class, () -> {
            new DocumentId(documentIdInput);
        });
    }
}