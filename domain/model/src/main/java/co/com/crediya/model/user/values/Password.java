package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.utils.ValidationFieldDomain;

import static co.com.crediya.model.user.utils.Constantes.NAME_EMPTY;
import static co.com.crediya.model.user.utils.Constantes.NAME_NULL;

public class Password {
    private String password;

    public Password(String password) throws ConstructionDomainException {
        // Verificar que el nombre no llegue nulo
        ValidationFieldDomain.validateNotNull(password,NAME_NULL);

        // Verificar que el nombre no llegue vacío
        if (password.isBlank()){
            throw new ConstructionDomainException(NAME_EMPTY);
        }
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
