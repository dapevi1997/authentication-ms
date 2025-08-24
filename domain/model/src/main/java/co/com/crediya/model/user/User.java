package co.com.crediya.model.user;

import co.com.crediya.model.user.values.*;

public class User {
    private Name name;
    private LastName lastName;
    private Email email;
    private DocumentId documentId;
    private Phone phone;
    private BaseSalary baseSalary;

    public User(Name name, LastName lastName, Email email, DocumentId documentId, Phone phone, BaseSalary baseSalary) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.documentId = documentId;
        this.phone = phone;
        this.baseSalary = baseSalary;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public LastName getLastName() {
        return lastName;
    }

    public void setLastName(LastName lastName) {
        this.lastName = lastName;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public DocumentId getDocumentId() {
        return documentId;
    }

    public void setDocumentId(DocumentId documentId) {
        this.documentId = documentId;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public BaseSalary getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(BaseSalary baseSalary) {
        this.baseSalary = baseSalary;
    }

    @Override
    public String toString() {
        return "User{" +
                "name=" + name +
                ", lastName=" + lastName +
                ", email=" + email +
                ", documentId=" + documentId +
                ", phone=" + phone +
                ", baseSalary=" + baseSalary +
                '}';
    }
}
