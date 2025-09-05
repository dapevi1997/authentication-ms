package co.com.crediya.api.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RegisterUserResponseDtoTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        RegisterUserResponseDto dto = new RegisterUserResponseDto(
                "1", "2", "Usuario registrado", "user@mail.com",
                "Daniel", "2000000", "123456789"
        );

        assertThat(dto.getIdUser()).isEqualTo("1");
        assertThat(dto.getIdRole()).isEqualTo("2");
        assertThat(dto.getMessage()).isEqualTo("Usuario registrado");
        assertThat(dto.getEmail()).isEqualTo("user@mail.com");
        assertThat(dto.getNombre()).isEqualTo("Daniel");
        assertThat(dto.getSalarioBase()).isEqualTo("2000000");
        assertThat(dto.getTimestamp()).isEqualTo("123456789");
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        RegisterUserResponseDto dto = new RegisterUserResponseDto();
        dto.setIdUser("10");
        dto.setIdRole("5");
        dto.setMessage("OK");
        dto.setEmail("test@mail.com");
        dto.setNombre("Juan");
        dto.setSalarioBase("500000");

        assertThat(dto.getIdUser()).isEqualTo("10");
        assertThat(dto.getIdRole()).isEqualTo("5");
        assertThat(dto.getMessage()).isEqualTo("OK");
        assertThat(dto.getEmail()).isEqualTo("test@mail.com");
        assertThat(dto.getNombre()).isEqualTo("Juan");
        assertThat(dto.getSalarioBase()).isEqualTo("500000");
        assertThat(dto.getTimestamp()).isNotNull();
    }

    @Test
    void testBuilderAndDefaultTimestamp() {
        RegisterUserResponseDto dto = RegisterUserResponseDto.builder()
                .idUser("20")
                .idRole("3")
                .message("Created")
                .email("builder@mail.com")
                .nombre("Maria")
                .salarioBase("3000000")
                .build();

        assertThat(dto.getIdUser()).isEqualTo("20");
        assertThat(dto.getIdRole()).isEqualTo("3");
        assertThat(dto.getMessage()).isEqualTo("Created");
        assertThat(dto.getEmail()).isEqualTo("builder@mail.com");
        assertThat(dto.getNombre()).isEqualTo("Maria");
        assertThat(dto.getSalarioBase()).isEqualTo("3000000");
        assertThat(dto.getTimestamp()).isNotNull(); // valor por defecto
    }

    @Test
    void testToBuilder() {
        RegisterUserResponseDto dto = RegisterUserResponseDto.builder()
                .idUser("1")
                .idRole("2")
                .message("Success")
                .email("original@mail.com")
                .nombre("Daniel")
                .salarioBase("1000000")
                .build();

        RegisterUserResponseDto modified = dto.toBuilder()
                .email("modified@mail.com")
                .build();

        assertThat(modified.getEmail()).isEqualTo("modified@mail.com");
        assertThat(modified.getIdUser()).isEqualTo("1"); // se conserva del original
        assertThat(modified.getMessage()).isEqualTo("Success");
    }
}
