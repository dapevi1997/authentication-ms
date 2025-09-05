package co.com.crediya.r2dbc.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class UserEntityTest {
    @Test
    void testAllArgsConstructorAndGetters() {
        LocalDate birthdate = LocalDate.of(1990, 1, 1);
        LocalDate createdAt = LocalDate.now();

        UserEntity user = new UserEntity(
                1L,
                "Daniel",
                "Pérez",
                birthdate,
                "Calle 123",
                "daniel@test.com",
                "securePass",
                1234567890L,
                3001234567L,
                new BigDecimal("2500000"),
                2L,
                createdAt
        );

        assertThat(user.getIdUser()).isEqualTo(1L);
        assertThat(user.getName()).isEqualTo("Daniel");
        assertThat(user.getLastname()).isEqualTo("Pérez");
        assertThat(user.getBirthdate()).isEqualTo(birthdate);
        assertThat(user.getAddress()).isEqualTo("Calle 123");
        assertThat(user.getEmail()).isEqualTo("daniel@test.com");
        assertThat(user.getPassword()).isEqualTo("securePass");
        assertThat(user.getDocumentoIdentidad()).isEqualTo(1234567890L);
        assertThat(user.getPhone()).isEqualTo(3001234567L);
        assertThat(user.getBaseSalary()).isEqualByComparingTo(new BigDecimal("2500000"));
        assertThat(user.getIdRol()).isEqualTo(2L);
        assertThat(user.getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        LocalDate birthdate = LocalDate.of(2000, 5, 15);
        LocalDate createdAt = LocalDate.of(2023, 1, 1);

        UserEntity user = new UserEntity();
        user.setIdUser(2L);
        user.setName("Laura");
        user.setLastname("García");
        user.setBirthdate(birthdate);
        user.setAddress("Carrera 45");
        user.setEmail("laura@test.com");
        user.setPassword("12345");
        user.setDocumentoIdentidad(9876543210L);
        user.setPhone(3012345678L);
        user.setBaseSalary(new BigDecimal("1800000"));
        user.setIdRol(3L);
        user.setCreatedAt(createdAt);

        assertThat(user.getIdUser()).isEqualTo(2L);
        assertThat(user.getName()).isEqualTo("Laura");
        assertThat(user.getLastname()).isEqualTo("García");
        assertThat(user.getBirthdate()).isEqualTo(birthdate);
        assertThat(user.getAddress()).isEqualTo("Carrera 45");
        assertThat(user.getEmail()).isEqualTo("laura@test.com");
        assertThat(user.getPassword()).isEqualTo("12345");
        assertThat(user.getDocumentoIdentidad()).isEqualTo(9876543210L);
        assertThat(user.getPhone()).isEqualTo(3012345678L);
        assertThat(user.getBaseSalary()).isEqualByComparingTo(new BigDecimal("1800000"));
        assertThat(user.getIdRol()).isEqualTo(3L);
        assertThat(user.getCreatedAt()).isEqualTo(createdAt);
    }

    @Test
    void testObjectsAreIndependent() {
        UserEntity user1 = new UserEntity();
        user1.setIdUser(1L);
        user1.setName("Carlos");

        UserEntity user2 = new UserEntity();
        user2.setIdUser(2L);
        user2.setName("María");

        assertThat(user1).isNotSameAs(user2);
        assertThat(user1.getIdUser()).isNotEqualTo(user2.getIdUser());
        assertThat(user1.getName()).isNotEqualTo(user2.getName());
    }
}