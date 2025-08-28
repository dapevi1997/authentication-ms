package co.com.crediya.model.role.values;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import co.com.crediya.model.user.exception.ConstructionDomainException;

class IdRoleTest {
    @Test
    void idRoleOk() throws ConstructionDomainException {
        String idInput = "10";
        IdRole idRole = new IdRole(idInput);
        assertEquals(Long.valueOf(idInput), idRole.getIdRole());
    }

    @Test
    void idRoleNull() {
        String idInput = null;
        assertThrows(ConstructionDomainException.class, () -> new IdRole(idInput));
    }

    @Test
    void idRoleNotNumeric() {
        String idInput = "abc";
        assertThrows(ConstructionDomainException.class, () -> new IdRole(idInput));
    }
}
