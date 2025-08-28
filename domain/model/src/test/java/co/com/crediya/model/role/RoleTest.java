package co.com.crediya.model.role;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import co.com.crediya.model.role.values.Description;
import co.com.crediya.model.role.values.IdRole;
import co.com.crediya.model.role.values.Name;
import co.com.crediya.model.user.exception.ConstructionDomainException;

class RoleTest {
    @Test
    void roleSettersAndGetters() throws ConstructionDomainException {
        IdRole idRole = new IdRole("1");
        Name name = new Name("Admin");
        Description description = new Description("Administrador");

        Role role = new Role();
        role.setIdRole(idRole);
        role.setNameRole(name);
        role.setDescriptionRole(description);

        assertEquals(idRole, role.getIdRole());
        assertEquals(name, role.getNameRole());
        assertEquals(description, role.getDescriptionRole());
    }
}
