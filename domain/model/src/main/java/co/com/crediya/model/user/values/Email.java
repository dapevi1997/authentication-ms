package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.utils.ValidationFieldDomain;

import static co.com.crediya.model.user.utils.Constantes.EMAIL_NULL;

public class Email {
    private String emailUser;

    public Email(String emailUser) throws ConstructionDomainException {
        // Verificar que el email no sea null
        ValidationFieldDomain.validateNotNull(emailUser, EMAIL_NULL);
        //Verificar que email tenga formato correcto
        ValidationFieldDomain.verifyEmailFormat(emailUser);

        this.emailUser = emailUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    @Override
    public String toString() {
        return emailUser;
    }
}
