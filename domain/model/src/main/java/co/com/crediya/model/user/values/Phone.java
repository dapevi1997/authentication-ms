package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.UserContructionException;
import co.com.crediya.model.user.utils.UtilUsers;

import java.util.Objects;

import static co.com.crediya.model.user.utils.Constantes.PHONE_NULL;

public class Phone {
    private Long phoneUser;

    public Phone(String phoneUser) throws UserContructionException {
        // Validar que el telefono del usuario no sea nulo
        UtilUsers.validateNotNull(phoneUser, PHONE_NULL);
        // Validar que sea un valor numérico
        this.phoneUser = UtilUsers.validateToLong(phoneUser);
    }

    public Long getPhoneUser() {
        return phoneUser;
    }

    public void setPhoneUser(Long phoneUser) {
        this.phoneUser = phoneUser;
    }

    @Override
    public String toString() {
        return phoneUser.toString();
    }
}
