package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.utils.ValidationFieldDomain;

import static co.com.crediya.model.user.utils.Constantes.ID_USER_BAD_FORMAT;
import static co.com.crediya.model.user.utils.Constantes.ID_USER_NULL;

public class IdUser {
    private Long idUser;

    public IdUser(String idUser) throws ConstructionDomainException {
        // Se hacen las validaciones del id
        ValidationFieldDomain.validateNotNull(idUser, ID_USER_NULL);
        this.idUser = ValidationFieldDomain.validateToLong(idUser, ID_USER_BAD_FORMAT);
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }
}
