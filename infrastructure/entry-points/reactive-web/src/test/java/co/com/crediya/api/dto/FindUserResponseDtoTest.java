package co.com.crediya.api.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class FindUserResponseDtoTest {

    @Test
    void testBuilderCreatesObject() {
        LocalDate birthday = LocalDate.of(1997, 5, 20);
        FindUserResponseDto dto = FindUserResponseDto.builder()
                .idUser(1L)
                .name("Daniel")
                .lastName("Pérez")
                .email("test@mail.com")
                .birthday(birthday)
                .address("Calle 123")
                .documentId("123456789")
                .phone("3001234567")
                .baseSalary(BigDecimal.valueOf(5000000))
                .idRole(2L)
                .build();

        assertThat(dto.getIdUser()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Daniel");
        assertThat(dto.getLastName()).isEqualTo("Pérez");
        assertThat(dto.getEmail()).isEqualTo("test@mail.com");
        assertThat(dto.getBirthday()).isEqualTo(birthday);
        assertThat(dto.getAddress()).isEqualTo("Calle 123");
        assertThat(dto.getDocumentId()).isEqualTo("123456789");
        assertThat(dto.getPhone()).isEqualTo("3001234567");
        assertThat(dto.getBaseSalary()).isEqualTo(BigDecimal.valueOf(5000000));
        assertThat(dto.getIdRole()).isEqualTo(2L);
    }

    @Test
    void testAllArgsConstructor() {
        LocalDate birthday = LocalDate.of(2000, 1, 1);
        FindUserResponseDto dto = new FindUserResponseDto(
                10L, "Ana", "Gómez", "ana@mail.com", birthday,
                "Carrera 45", "987654321", "3109876543", BigDecimal.TEN, 5L
        );

        assertThat(dto.getIdUser()).isEqualTo(10L);
        assertThat(dto.getName()).isEqualTo("Ana");
        assertThat(dto.getLastName()).isEqualTo("Gómez");
        assertThat(dto.getEmail()).isEqualTo("ana@mail.com");
        assertThat(dto.getBirthday()).isEqualTo(birthday);
        assertThat(dto.getAddress()).isEqualTo("Carrera 45");
        assertThat(dto.getDocumentId()).isEqualTo("987654321");
        assertThat(dto.getPhone()).isEqualTo("3109876543");
        assertThat(dto.getBaseSalary()).isEqualTo(BigDecimal.TEN);
        assertThat(dto.getIdRole()).isEqualTo(5L);
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        FindUserResponseDto dto = new FindUserResponseDto();
        dto.setIdUser(99L);
        dto.setName("Carlos");

        assertThat(dto.getIdUser()).isEqualTo(99L);
        assertThat(dto.getName()).isEqualTo("Carlos");
    }

    @Test
    void testToBuilderCopiesAndModifies() {
        FindUserResponseDto original = FindUserResponseDto.builder()
                .idUser(1L)
                .name("Daniel")
                .lastName("Pérez")
                .build();

        FindUserResponseDto modified = original.toBuilder()
                .name("Juan")
                .build();

        assertThat(modified.getIdUser()).isEqualTo(1L);
        assertThat(modified.getLastName()).isEqualTo("Pérez");
        assertThat(modified.getName()).isEqualTo("Juan");
    }
}
