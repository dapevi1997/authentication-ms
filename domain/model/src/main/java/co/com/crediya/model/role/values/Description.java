package co.com.crediya.model.role.values;

import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.utils.ValidationFieldDomain;

import static co.com.crediya.model.role.util.Constantes.DESCRIPTION_EMPTY;
import static co.com.crediya.model.role.util.Constantes.DESCRIPTION_NULL;

public class Description {
    private String descriptionRole;

    public Description(String descriptionRole) throws ConstructionDomainException {
        // Validación de la descripción del rol
        ValidationFieldDomain.validateNotNull(descriptionRole, DESCRIPTION_NULL);
        ValidationFieldDomain.validateNotEmpty(descriptionRole,DESCRIPTION_EMPTY);
        this.descriptionRole = descriptionRole;
    }

    public String getDescriptionRole() {
        return descriptionRole;
    }

    public void setDescriptionRole(String descriptionRole) {
        this.descriptionRole = descriptionRole;
    }

    @Override
    public String toString() {
        return descriptionRole;
    }
}
