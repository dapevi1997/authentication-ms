package co.com.crediya.model.user.values;

import co.com.crediya.model.user.exception.UserContructionException;
import co.com.crediya.model.user.utils.UtilUsers;

import java.math.BigDecimal;

import static co.com.crediya.model.user.utils.Constantes.BASE_SALARY_NULL;

public class BaseSalary {
    private BigDecimal baseSalaryUser;

    public BaseSalary(String baseSalaryUser) throws UserContructionException {
        // Validar que el salario base del usuario no sea nulo
        UtilUsers.validateNotNull(baseSalaryUser, BASE_SALARY_NULL);
        // Validar que el salario esté dentro del rango requerido y sea valor numérico
        this.baseSalaryUser = UtilUsers.validateBaseSalary(baseSalaryUser);
    }

    public BigDecimal getBaseSalaryUser() {
        return baseSalaryUser;
    }

    public void setBaseSalaryUser(BigDecimal baseSalaryUser) {
        this.baseSalaryUser = baseSalaryUser;
    }

    @Override
    public String toString() {
        return baseSalaryUser.toString();
    }
}
