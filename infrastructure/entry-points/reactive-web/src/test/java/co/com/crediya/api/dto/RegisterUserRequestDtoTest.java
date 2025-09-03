package co.com.crediya.api.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterUserRequestDtoTest {

    private static Validator validator;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testGettersSettersAndToString() {
        RegisterUserRequestDto dto = new RegisterUserRequestDto();
        dto.setName("Daniel");
        dto.setLastName("Perez");
        dto.setAddress("CR 21#56-78");
        dto.setEmail("user@mail.com");
        dto.setPassword("pass123");
        dto.setDocumentId("123456");
        dto.setPhone("3001234567");
        dto.setBaseSalary("2000000");
        dto.setUserBirthday("1990-05-21");
        dto.setIdRole("1");

        assertThat(dto.getName()).isEqualTo("Daniel");
        assertThat(dto.getLastName()).isEqualTo("Perez");
        assertThat(dto.getAddress()).isEqualTo("CR 21#56-78");
        assertThat(dto.getEmail()).isEqualTo("user@mail.com");
        assertThat(dto.getPassword()).isEqualTo("pass123");
        assertThat(dto.getDocumentId()).isEqualTo("123456");
        assertThat(dto.getPhone()).isEqualTo("3001234567");
        assertThat(dto.getBaseSalary()).isEqualTo("2000000");
        assertThat(dto.getUserBirthday()).isEqualTo("1990-05-21");
        assertThat(dto.getIdRole()).isEqualTo("1");
    }

    @Test
    void testValidationFailsWhenFieldsAreBlankOrNull() {
        RegisterUserRequestDto dto = new RegisterUserRequestDto(); // vacío → debería fallar

        Set<ConstraintViolation<RegisterUserRequestDto>> violations = validator.validate(dto);

        assertThat(violations).isNotEmpty();
        assertThat(violations.stream().map(ConstraintViolation::getMessage))
                .anyMatch(msg -> msg.contains("no puede ser nulo"));
    }

    @Test
    void testValidationPassesWithValidData() {
        RegisterUserRequestDto dto = new RegisterUserRequestDto();
        dto.setName("Daniel");
        dto.setLastName("Perez");
        dto.setAddress("CR 21#56-78");
        dto.setEmail("valid@mail.com");
        dto.setPassword("securePass");
        dto.setDocumentId("987654");
        dto.setPhone("3219876543");
        dto.setBaseSalary("1000000");
        dto.setUserBirthday("1995-12-31");
        dto.setIdRole("2");

        Set<ConstraintViolation<RegisterUserRequestDto>> violations = validator.validate(dto);

        assertThat(violations).isEmpty();
    }

    @Test
    void testJsonSerializationAndDeserialization() throws Exception {
        RegisterUserRequestDto dto = new RegisterUserRequestDto();
        dto.setName("Daniel");
        dto.setLastName("Perez");
        dto.setAddress("CR 21#56-78");
        dto.setEmail("user@mail.com");
        dto.setPassword("secret");
        dto.setDocumentId("123456");
        dto.setPhone("3001234567");
        dto.setBaseSalary("1500000");
        dto.setUserBirthday("1990-05-21");
        dto.setIdRole("1");

        String json = objectMapper.writeValueAsString(dto);
        assertThat(json).contains("\"nombre\":\"Daniel\"");
        assertThat(json).contains("\"apellido\":\"Perez\"");
        assertThat(json).contains("\"email\":\"user@mail.com\"");

        RegisterUserRequestDto deserialized = objectMapper.readValue(json, RegisterUserRequestDto.class);
        assertThat(deserialized.getName()).isEqualTo("Daniel");
        assertThat(deserialized.getEmail()).isEqualTo("user@mail.com");
    }
}
