package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.UserContructionException;
import co.com.crediya.model.user.utils.UtilUsers;

import java.util.Objects;

import static co.com.crediya.model.user.utils.Constantes.*;

public class LastName {
    private String lastNameUser;

    public LastName(String lastNameUser) throws UserContructionException {
        // Verificar que el apellido no llegue nulo
        UtilUsers.validateNotNull(lastNameUser, LASTNAME_NULL);
        // Verificar que el apellido no llegue vacío
        if (lastNameUser.isBlank()){
            throw new UserContructionException(LASTNAME_EMPTY);
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
