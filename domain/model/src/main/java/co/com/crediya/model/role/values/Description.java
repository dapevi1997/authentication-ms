package co.com.crediya.model.role.values;

public class Description {
    private String descriptionRole;

    public Description(String descriptionRole) {
        this.descriptionRole = descriptionRole;
    }

    public String getDescriptionRole() {
        return descriptionRole;
    }

    public void setDescriptionRole(String descriptionRole) {
        this.descriptionRole = descriptionRole;
    }

    @Override
    public String toString() {
        return descriptionRole;
    }
}
