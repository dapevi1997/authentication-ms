package co.com.crediya.model.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import co.com.crediya.model.user.values.*;
import org.junit.jupiter.api.Test;
import co.com.crediya.model.user.exception.ConstructionDomainException;

class UserTest {
    @Test
    void userSettersAndGetters() throws ConstructionDomainException {
        Name name = new Name("Juan");
        LastName lastName = new LastName("Pérez");
        Email email = new Email("juan@mail.com");
        Birthday birthday = new Birthday("2000-01-01");
        Address address = new Address("Calle 1");
        DocumentId documentId = new DocumentId("12345");
        Phone phone = new Phone("1234567890");
        BaseSalary baseSalary = new BaseSalary("1000.0");
        IdRole idRole = new IdRole("1");
        IdUser idUser = new IdUser("99");

        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setBirthday(birthday);
        user.setAddress(address);
        user.setDocumentId(documentId);
        user.setPhone(phone);
        user.setBaseSalary(baseSalary);
        user.setIdRole(idRole);
        user.setIdUser(idUser);

        assertEquals(name, user.getName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals(birthday, user.getBirthday());
        assertEquals(address, user.getAddress());
        assertEquals(documentId, user.getDocumentId());
        assertEquals(phone, user.getPhone());
        assertEquals(baseSalary, user.getBaseSalary());
        assertEquals(idRole, user.getIdRole());
        assertEquals(idUser, user.getIdUser());
    }

    @Test
    void userAllArgsConstructor() throws ConstructionDomainException {
        Name name = new Name("Ana");
        LastName lastName = new LastName("Gómez");
        Email email = new Email("ana@mail.com");
        Birthday birthday = new Birthday("1990-12-12");
        Address address = new Address("Calle 2");
        DocumentId documentId = new DocumentId("54321");
        Phone phone = new Phone("9876543210");
        BaseSalary baseSalary = new BaseSalary("2000.0");
        IdRole idRole = new IdRole("2");
        Password password = new Password("password");

        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setBirthday(birthday);
        user.setAddress(address);
        user.setDocumentId(documentId);
        user.setPhone(phone);
        user.setBaseSalary(baseSalary);
        user.setIdRole(idRole);
        user.setPassword(password);

        assertEquals(name, user.getName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals(birthday, user.getBirthday());
        assertEquals(address, user.getAddress());
        assertEquals(documentId, user.getDocumentId());
        assertEquals(phone, user.getPhone());
        assertEquals(baseSalary, user.getBaseSalary());
        assertEquals(idRole, user.getIdRole());
        assertEquals(password, user.getPassword());
    }
}
