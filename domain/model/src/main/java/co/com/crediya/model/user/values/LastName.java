package co.com.crediya.model.user.values;

import java.util.Objects;

import static co.com.crediya.model.user.utils.Constantes.*;

public class LastName {
    private String lastNameUser;

    public LastName(String lastNameUser) {
        // Verificar que el apellido no llegue nulo
        Objects.requireNonNull(lastNameUser, LASTNAME_NULL);

        // Verificar que el apellido no llegue vacío
        if (lastNameUser.isBlank()){
            throw new IllegalArgumentException(LASTNAME_EMPTY);
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
