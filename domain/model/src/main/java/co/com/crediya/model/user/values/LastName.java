package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.utils.ValidationFieldDomain;

import static co.com.crediya.model.user.utils.Constantes.*;

public class LastName {
    private String lastNameUser;

    public LastName(String lastNameUser) throws ConstructionDomainException {
        // Verificar que el apellido no llegue nulo
        ValidationFieldDomain.validateNotNull(lastNameUser, LASTNAME_NULL);
        // Verificar que el apellido no llegue vacío
        if (lastNameUser.isBlank()){
            throw new ConstructionDomainException(LASTNAME_EMPTY);
        }
        this.lastNameUser = lastNameUser;
    }

    public String getLastNameUser() {
        return lastNameUser;
    }

    public void setLastNameUser(String lastNameUser) {
        this.lastNameUser = lastNameUser;
    }

    @Override
    public String toString() {
        return lastNameUser;
    }
}
