package co.com.crediya.model.user.values;

import java.util.Objects;

import static co.com.crediya.model.user.utils.Constantes.*;

public class Name {
    private String nameUser;

    public Name(String nameUser) {
        // Verificar que el nombre no llegue nulo
        Objects.requireNonNull(nameUser, NAME_NULL);

        // Verificar que el nombre no llegue vacío
        if (nameUser.isBlank()){
            throw new IllegalArgumentException(NAME_EMPTY);
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
