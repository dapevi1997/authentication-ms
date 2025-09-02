package co.com.crediya.model.role.values;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import co.com.crediya.model.user.exception.ConstructionDomainException;

class DescriptionTest {
    @Test
    void descriptionRoleOk() throws ConstructionDomainException {
        String descInput = "Rol de administrador";
        Description desc = new Description(descInput);
        assertEquals(descInput, desc.getDescriptionRole());
    }

    @Test
    void descriptionRoleNull() {
        String descInput = null;
        assertThrows(ConstructionDomainException.class, () -> new Description(descInput));
    }

    @Test
    void descriptionRoleEmpty() {
        String descInput = "";
        assertThrows(ConstructionDomainException.class, () -> new Description(descInput));
    }
}
