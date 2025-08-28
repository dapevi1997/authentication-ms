package co.com.crediya.model.role.values;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import co.com.crediya.model.user.exception.ConstructionDomainException;

class NameTest {
    @Test
    void nameRoleOk() throws ConstructionDomainException {
        String nameInput = "Admin";
        Name name = new Name(nameInput);
        assertEquals(nameInput, name.getNameRole());
    }

    @Test
    void nameRoleNull() {
        String nameInput = null;
        assertThrows(ConstructionDomainException.class, () -> new Name(nameInput));
    }

    @Test
    void nameRoleEmpty() {
        String nameInput = "";
        assertThrows(ConstructionDomainException.class, () -> new Name(nameInput));
    }
}
