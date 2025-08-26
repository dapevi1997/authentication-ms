package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.ConstructionDomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {
    // La dirección es correcta
    @Test
    void addressOk() throws ConstructionDomainException {
        // Arrange
        String adressInput = "CR64 #25";

        // Act
        Address address = new Address(adressInput);

        // Assert
        assertEquals(adressInput, address.getAdress());
    }

    // La dirección es null
    @Test
    void addressNull(){
        // Arrange
        String addresInput = null;

        // Act and Assert
        assertThrows(ConstructionDomainException.class, () -> {
            new Address(addresInput);
        });
    }

    // La dirección  es vacía
    @Test
    void adressEmpty(){
        // Arrange
        String adressInput = "";

        // Act and Assert
        assertThrows(ConstructionDomainException.class, () -> {
            new Address(adressInput);
        });
    }
}