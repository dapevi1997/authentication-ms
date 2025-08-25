package co.com.crediya.model.user;

import co.com.crediya.model.user.values.*;

public class User {
    private IdUser idUser;
    private Name name;
    private LastName lastName;
    private Email email;
    private Birthday birthday;
    private Address address;
    private DocumentId documentId;
    private Phone phone;
    private BaseSalary baseSalary;
    private IdRole idRole;

    public User() {
    }

    public User(Name name, LastName lastName, Email email, Birthday birthday, Address address, DocumentId documentId, Phone phone, BaseSalary baseSalary, IdRole idRole) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.address = address;
        this.documentId = documentId;
        this.phone = phone;
        this.baseSalary = baseSalary;
        this.idRole = idRole;
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

    public IdUser getIdUser() {
        return idUser;
    }

    public void setIdUser(IdUser idUser) {
        this.idUser = idUser;
    }

    public IdRole getIdRole() {
        return idRole;
    }

    public void setIdRole(IdRole idRole) {
        this.idRole = idRole;
    }

    public Birthday getBirthday() {
        return birthday;
    }

    public void setBirthday(Birthday birthday) {
        this.birthday = birthday;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", name=" + name +
                ", lastName=" + lastName +
                ", email=" + email +
                ", birthday=" + birthday +
                ", address=" + address +
                ", documentId=" + documentId +
                ", phone=" + phone +
                ", baseSalary=" + baseSalary +
                ", idRole=" + idRole +
                '}';
    }
}
