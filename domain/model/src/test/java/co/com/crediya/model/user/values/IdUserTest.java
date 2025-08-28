package co.com.crediya.model.user.values;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import co.com.crediya.model.user.exception.ConstructionDomainException;

class IdUserTest {
    @Test
    void idUserOk() throws ConstructionDomainException {
        String idInput = "123";
        IdUser idUser = new IdUser(idInput);
        assertEquals(Long.valueOf(idInput), idUser.getIdUser());
    }

    @Test
    void idUserNull() {
        String idInput = null;
        assertThrows(ConstructionDomainException.class, () -> new IdUser(idInput));
    }

    @Test
    void idUserNotNumeric() {
        String idInput = "abc";
        assertThrows(ConstructionDomainException.class, () -> new IdUser(idInput));
    }
}
