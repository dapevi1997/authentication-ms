package co.com.crediya.model.user.values;

import java.util.Objects;

import static co.com.crediya.model.user.utils.Constantes.PHONE_NULL;

public class Phone {
    private Long phoneUser;

    public Phone(Long phoneUser) {
        // Validar que el telefono del usuario no sea nulo
        Objects.requireNonNull(phoneUser, PHONE_NULL);
        this.phoneUser = phoneUser;
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
