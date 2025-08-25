package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.utils.ValidationFieldDomain;

import static co.com.crediya.model.user.utils.Constantes.ADDRESS_USER_EMPTY;
import static co.com.crediya.model.user.utils.Constantes.ADDRESS_USER_NULL;

public class Address {
    private String adress;

    public Address(String adress) throws ConstructionDomainException {
        ValidationFieldDomain.validateNotNull(adress, ADDRESS_USER_NULL);
        ValidationFieldDomain.validateNotEmpty(adress, ADDRESS_USER_EMPTY);
        this.adress = adress;
    }

    public String getAdress() {
        return adress;
    }
}
