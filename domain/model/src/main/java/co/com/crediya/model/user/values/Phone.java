package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.utils.ValidationFieldDomain;

import static co.com.crediya.model.user.utils.Constantes.PHONE_BAD_FORMAT;
import static co.com.crediya.model.user.utils.Constantes.PHONE_NULL;

public class Phone {
    private Long phoneUser;

    public Phone(String phoneUser) throws ConstructionDomainException {
        // Validar que el telefono del usuario no sea nulo
        ValidationFieldDomain.validateNotNull(phoneUser, PHONE_NULL);
        // Validar que sea un valor numérico
        this.phoneUser = ValidationFieldDomain.validateToLong(phoneUser, PHONE_BAD_FORMAT);
    }

    public Long getPhoneUser() {
        return phoneUser;
    }

    public void setPhoneUser(Long phoneUser) {
        this.phoneUser = phoneUser;
    }

}
