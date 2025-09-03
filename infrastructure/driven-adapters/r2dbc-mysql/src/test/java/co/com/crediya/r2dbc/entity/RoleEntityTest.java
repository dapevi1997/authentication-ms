package co.com.crediya.r2dbc.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RoleEntityTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        RoleEntity role = new RoleEntity(1L, "ADMIN", "Administrator role");

        assertThat(role.getIdRole()).isEqualTo(1L);
        assertThat(role.getName()).isEqualTo("ADMIN");
        assertThat(role.getDescription()).isEqualTo("Administrator role");
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        RoleEntity role = new RoleEntity();
        role.setIdRole(2L);
        role.setName("USER");
        role.setDescription("User role");

        assertThat(role.getIdRole()).isEqualTo(2L);
        assertThat(role.getName()).isEqualTo("USER");
        assertThat(role.getDescription()).isEqualTo("User role");
    }

    @Test
    void testObjectsAreIndependent() {
        RoleEntity role1 = new RoleEntity(1L, "ADMIN", "Administrator role");
        RoleEntity role2 = new RoleEntity(2L, "USER", "User role");

        assertThat(role1).isNotSameAs(role2);
        assertThat(role1.getIdRole()).isNotEqualTo(role2.getIdRole());
        assertThat(role1.getName()).isNotEqualTo(role2.getName());
    }
}