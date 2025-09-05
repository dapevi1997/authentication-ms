package co.com.crediya.api.util;

import co.com.crediya.api.dto.FindUserByEmailResponseDto;
import co.com.crediya.api.dto.RegisterUserRequestDto;
import co.com.crediya.api.security.UserPrincipal;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.values.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class CustomMapperWebFluxTest {

    private CustomMapperWebFlux mapper;

    @BeforeEach
    void setUp() {
        mapper = new CustomMapperWebFlux();
    }

    @Test
    void testUserToUserPrincipal() throws ConstructionDomainException {
        // Arrange
        User user = new User();
        user.setEmail(new Email("test@mail.com"));
        user.setPassword(new Password("12345"));
        user.setIdRole(new IdRole("2"));

        // Act
        UserPrincipal principal = CustomMapperWebFlux.userToUserPrincipal(user);

        // Assert
        assertThat(principal).isNotNull();
        assertThat(principal.getEmail()).isEqualTo("test@mail.com");
        assertThat(principal.getPassword()).isEqualTo("12345");
        assertThat(principal.getIdRole()).isEqualTo(2L);
    }

    @Test
    void testRegisterUserRequestDtoToUser() throws Exception {
        // Arrange
        RegisterUserRequestDto dto = RegisterUserRequestDto.builder()
                .name("Daniel")
                .lastName("Perez")
                .email("daniel@mail.com")
                .userBirthday(LocalDate.of(1997, 5, 10).toString())
                .address("Calle 123")
                .documentId("12345678")
                .baseSalary("500")
                .idRole("1")
                .phone("987654321")
                .password("secret")
                .build();

        // Act
        User user = mapper.registerUserRequestDtoToUser(dto);

        // Assert
        assertThat(user).isNotNull();
        assertThat(user.getName().getNameUser()).isEqualTo("Daniel");
        assertThat(user.getLastName().getLastNameUser()).isEqualTo("Perez");
        assertThat(user.getEmail().getEmailUser()).isEqualTo("daniel@mail.com");
        assertThat(user.getBirthday().getUserBirthday()).isEqualTo(LocalDate.of(1997, 5, 10));
        assertThat(user.getAddress().getAdress()).isEqualTo("Calle 123");
        assertThat(user.getDocumentId().getDocumentoIdentidadUsuario().toString()).isEqualTo("12345678");
        assertThat(user.getBaseSalary().getBaseSalaryUser()).isEqualTo(BigDecimal.valueOf(500));
        assertThat(user.getIdRole().getIdRole()).isEqualTo(1L);
        assertThat(user.getPhone().getPhoneUser().toString()).isEqualTo("987654321");
        assertThat(user.getPassword().getPassword()).isEqualTo("secret");
    }

    @Test
    void testUserToFindUserByEmailDto() throws ConstructionDomainException {
        // Arrange
        User user = new User();
        user.setIdUser(new IdUser("1"));
        user.setName(new Name("Juan"));
        user.setLastName(new LastName("Lopez"));
        user.setEmail(new Email("juan@mail.com"));
        user.setBirthday(new Birthday(LocalDate.of(1990, 1, 1).toString()));
        user.setAddress(new Address("Carrera 45"));
        user.setDocumentId(new DocumentId("123"));
        user.setBaseSalary(new BaseSalary("7000"));
        user.setIdRole(new IdRole("3"));
        user.setPhone(new Phone("5550000"));

        // Act
        FindUserByEmailResponseDto dto = mapper.userToFindUSerByEmailDto(user);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.getIdUser()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Juan");
        assertThat(dto.getLastName()).isEqualTo("Lopez");
        assertThat(dto.getEmail()).isEqualTo("juan@mail.com");
        assertThat(dto.getBirthday()).isEqualTo(LocalDate.of(1990, 1, 1));
        assertThat(dto.getAddress()).isEqualTo("Carrera 45");
        assertThat(dto.getDocumentId()).isEqualTo("123");
        assertThat(dto.getBaseSalary()).isEqualTo(BigDecimal.valueOf(7000));
        assertThat(dto.getIdRole()).isEqualTo(3L);
        assertThat(dto.getPhone()).isEqualTo("5550000");
    }
}
