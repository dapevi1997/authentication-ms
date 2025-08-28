package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.utils.ValidationFieldDomain;

import java.time.LocalDate;

import static co.com.crediya.model.user.utils.Constantes.BIRTHDAY_BAD_FORMAT;
import static co.com.crediya.model.user.utils.Constantes.BIRTHDAY_USER_NULL;

public class Birthday {
    private LocalDate userBirthday;

    public Birthday(String userBirthday) throws ConstructionDomainException {
        // Validar fecha
        ValidationFieldDomain.validateNotNull(userBirthday,BIRTHDAY_USER_NULL);
        this.userBirthday = ValidationFieldDomain.validateBirthay(userBirthday, BIRTHDAY_BAD_FORMAT);
    }

    public LocalDate getUserBirthday() {
        return userBirthday;
    }
}
