package co.com.crediya.model.user.values;

import java.util.Objects;

import static co.com.crediya.model.user.utils.Constantes.BASE_SALARY_NULL;


public class BaseSalary {
    private Long baseSalaryUser;

    public BaseSalary(Long baseSalaryUser) {
        // Validar que el salario base del usuario no sea nulo
        Objects.requireNonNull(baseSalaryUser, BASE_SALARY_NULL);
        this.baseSalaryUser = baseSalaryUser;
    }

    public Long getBaseSalaryUser() {
        return baseSalaryUser;
    }

    public void setBaseSalaryUser(Long baseSalaryUser) {
        this.baseSalaryUser = baseSalaryUser;
    }

    @Override
    public String toString() {
        return baseSalaryUser.toString();
    }
}
