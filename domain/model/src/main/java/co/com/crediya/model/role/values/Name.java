package co.com.crediya.model.role.values;

import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.utils.ValidationFieldDomain;

import static co.com.crediya.model.role.util.Constantes.NAME_EMPTY;
import static co.com.crediya.model.role.util.Constantes.NAME_NULL;

public class Name {
    private String nameRole;

    public Name(String nameRole) throws ConstructionDomainException {
        // Se hacen las validaciones del nombre
        ValidationFieldDomain.validateNotNull(nameRole, NAME_NULL);
        ValidationFieldDomain.validateNotEmpty(nameRole, NAME_EMPTY);
        this.nameRole = nameRole;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    @Override
    public String toString() {
        return nameRole;
    }
}
