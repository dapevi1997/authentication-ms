package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.utils.ValidationFieldDomain;

import static co.com.crediya.model.user.utils.Constantes.ID_ROLE_BAD_FORMAT;
import static co.com.crediya.model.user.utils.Constantes.ID_ROLE_NULL;

public class IdRole {
    private Long idRole;

    public IdRole(String idRole) throws ConstructionDomainException {
        ValidationFieldDomain.validateNotNull(idRole,ID_ROLE_NULL);
        this.idRole = ValidationFieldDomain.validateToLong(idRole, ID_ROLE_BAD_FORMAT);
    }

    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    @Override
    public String toString() {
        return idRole.toString();
    }
}
