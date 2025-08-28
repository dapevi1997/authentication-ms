package co.com.crediya.api.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import co.com.crediya.api.dto.RegisterUserRequestDto;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.ConstructionDomainException;

class CustomMapperWebFluxTest {

    private CustomMapperWebFlux mapper;
    private RegisterUserRequestDto validDto;

    @BeforeEach
    void setUp() {
        mapper = new CustomMapperWebFlux();

        validDto = new RegisterUserRequestDto();
        validDto.setName("Juan");
        validDto.setLastName("Perez");
        validDto.setEmail("juan@test.com");
        validDto.setUserBirthday("1990-01-01");
        validDto.setAddress("Calle 123");
        validDto.setDocumentId("12345678");
        validDto.setBaseSalary("1000.00");
        validDto.setIdRole("1");
        validDto.setPhone("3001234567");
    }

    @Test
    void testRegisterUserRequestDtoToUserSuccess() throws ConstructionDomainException {
        User user = mapper.registerUserRequestDtoToUser(validDto);

        assertNotNull(user);
        assertEquals("Juan", user.getName().getNameUser());
        assertEquals("Perez", user.getLastName().getLastNameUser());
        assertEquals("juan@test.com", user.getEmail().getEmailUser());
        assertEquals("Calle 123", user.getAddress().getAdress());
        assertEquals(1L, user.getIdRole().getIdRole());
        assertEquals(3001234567L, user.getPhone().getPhoneUser());
    }

    @Test
    void testRegisterUserRequestDtoToUserWithNullName() {
        validDto.setName(null);

        assertThrows(ConstructionDomainException.class, () -> {
            mapper.registerUserRequestDtoToUser(validDto);
        });
    }

    @Test
    void testRegisterUserRequestDtoToUserWithInvalidEmail() {
        validDto.setEmail("invalid-email");

        assertThrows(ConstructionDomainException.class, () -> {
            mapper.registerUserRequestDtoToUser(validDto);
        });
    }

    @Test
    void testRegisterUserRequestDtoToUserWithInvalidSalary() {
        validDto.setBaseSalary("invalid-salary");

        assertThrows(ConstructionDomainException.class, () -> {
            mapper.registerUserRequestDtoToUser(validDto);
        });
    }

    @Test
    void testRegisterUserRequestDtoToUserWithNegativeSalary() {
        validDto.setBaseSalary("-1000.00");

        assertThrows(ConstructionDomainException.class, () -> {
            mapper.registerUserRequestDtoToUser(validDto);
        });
    }
}
