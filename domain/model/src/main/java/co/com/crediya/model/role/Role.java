package co.com.crediya.model.role;

import co.com.crediya.model.role.values.Description;
import co.com.crediya.model.role.values.IdRole;
import co.com.crediya.model.role.values.Name;

public class Role {
    private IdRole idRole;
    private Name nameRole;
    private Description descriptionRole;

    public Role(){}

    public IdRole getIdRole() {
        return idRole;
    }

    public void setIdRole(IdRole idRole) {
        this.idRole = idRole;
    }

    public Name getNameRole() {
        return nameRole;
    }

    public void setNameRole(Name nameRole) {
        this.nameRole = nameRole;
    }

    public Description getDescriptionRole() {
        return descriptionRole;
    }

    public void setDescriptionRole(Description descriptionRole) {
        this.descriptionRole = descriptionRole;
    }

    @Override
    public String toString() {
        return "Role{" +
                "idRole=" + idRole +
                ", nameRole=" + nameRole +
                ", descriptionRole=" + descriptionRole +
                '}';
    }
}
