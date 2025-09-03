package co.com.crediya.api.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class LoginRequestDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testGettersAndSetters() {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail("admin@mail.com");
        dto.setPassword("admin123");

        assertThat(dto.getEmail()).isEqualTo("admin@mail.com");
        assertThat(dto.getPassword()).isEqualTo("admin123");
    }

    @Test
    void testValidLoginRequest() {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail("user@mail.com");
        dto.setPassword("mypassword");

        Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void testEmailCannotBeNullOrBlank() {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail(null);
        dto.setPassword("somePass");

        Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(dto);
        assertThat(violations).extracting("message")
                .contains("El campo email no puede ser nulo");

        dto.setEmail("");
        violations = validator.validate(dto);
        assertThat(violations).extracting("message")
                .contains("El campo email no puede estar vacío");
    }

    @Test
    void testPasswordCannotBeNullOrBlank() {
        LoginRequestDto dto = new LoginRequestDto();
        dto.setEmail("user@mail.com");
        dto.setPassword(null);

        Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(dto);
        assertThat(violations).extracting("message")
                .contains("El campo password no puede ser nulo");

        dto.setPassword("");
        violations = validator.validate(dto);
        assertThat(violations).extracting("message")
                .contains("El clave password no puede estar vacío");
    }
}
