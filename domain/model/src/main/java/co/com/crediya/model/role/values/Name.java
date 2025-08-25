package co.com.crediya.model.role.values;

public class Name {
    private String nameRole;

    public Name(String nameRole) {
        this.nameRole = nameRole;
    }

    public String getNameRole() {
        return nameRole;
    }

    public void setNameRole(String nameRole) {
        this.nameRole = nameRole;
    }

    @Override
    public String toString() {
        return nameRole;
    }
}
