package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.utils.ValidationFieldDomain;

import java.time.LocalDate;

import static co.com.crediya.model.user.utils.Constantes.*;

public class CreatedAt {
    private LocalDate createdAt;

    public CreatedAt(String createdAt) throws ConstructionDomainException {
        // Validar fecha
        ValidationFieldDomain.validateNotNull(createdAt,CREATED_AT_NULL);
        this.createdAt = ValidationFieldDomain.validateBirthay(createdAt, CREATED_AT_BAD_FORMAT);
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }
}
