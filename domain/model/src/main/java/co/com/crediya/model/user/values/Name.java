package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.UserContructionException;
import co.com.crediya.model.user.utils.UtilUsers;

import static co.com.crediya.model.user.utils.Constantes.*;

public class Name {
    private String nameUser;

    public Name(String nameUser) throws UserContructionException {
        // Verificar que el nombre no llegue nulo
        UtilUsers.validateNotNull(nameUser,NAME_NULL);

        // Verificar que el nombre no llegue vacío
        if (nameUser.isBlank()){
            throw new UserContructionException(NAME_EMPTY);
        }
        this.nameUser = nameUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    @Override
    public String toString() {
        return nameUser;
    }
}
