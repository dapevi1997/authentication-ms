package co.com.crediya.model.user.values;

import co.com.crediya.model.user.utils.UtilUsers;

import java.math.BigDecimal;
import java.util.Objects;

import static co.com.crediya.model.user.utils.Constantes.BASE_SALARY_NULL;


public class BaseSalary {
    private BigDecimal baseSalaryUser;

    public BaseSalary(BigDecimal baseSalaryUser) {
        // Validar que el salario base del usuario no sea nulo
        Objects.requireNonNull(baseSalaryUser, BASE_SALARY_NULL);
        // Validar que el salario esté dentro del rango requerido
        UtilUsers.validateRangeSalary(baseSalaryUser);

        this.baseSalaryUser = baseSalaryUser;
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
