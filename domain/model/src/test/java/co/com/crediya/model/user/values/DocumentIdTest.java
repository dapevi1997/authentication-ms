package co.com.crediya.model.user.values;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DocumentIdTest {
    // El documento de identidad es correcto
    @Test
    void documentIdOk(){
        // Arrange
        Long documentIdInput = 1112L;

        // Act
        DocumentId documentId = new DocumentId(documentIdInput);

        // Assert
        assertEquals(documentIdInput, documentId.getDocumentoIdentidadUsuario());
    }

    // El documento de identidad es null
    @Test
    void documentIdNull(){
        // Arrange
        Long documentIdInput = null;

        // Act and Assert
        assertThrows(NullPointerException.class, () -> {
            new DocumentId(documentIdInput);
        });
    }
}