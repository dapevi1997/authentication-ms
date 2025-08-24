package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.UserContructionException;
import co.com.crediya.model.user.utils.UtilUsers;

import java.util.Objects;

import static co.com.crediya.model.user.utils.Constantes.EMAIL_NULL;

public class Email {
    private String emailUser;

    public Email(String emailUser) throws UserContructionException {
        // Verificar que el email no sea null
        UtilUsers.validateNotNull(emailUser, EMAIL_NULL);
        //Verificar que email tenga formato correcto
        UtilUsers.verifyEmailFormat(emailUser);

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
