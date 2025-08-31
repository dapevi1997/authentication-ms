package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.utils.ValidationFieldDomain;

import static co.com.crediya.model.user.utils.Constantes.*;

public class Name {
    private String nameUser;

    public Name(String nameUser) throws ConstructionDomainException {
        // Verificar que el nombre no llegue nulo
        ValidationFieldDomain.validateNotNull(nameUser,NAME_NULL);

        // Verificar que el nombre no llegue vacío
        if (nameUser.isBlank()){
            throw new ConstructionDomainException(NAME_EMPTY);
        }
        this.nameUser = nameUser;
    }

    public String getNameUser() {
        return nameUser;
    }
}
