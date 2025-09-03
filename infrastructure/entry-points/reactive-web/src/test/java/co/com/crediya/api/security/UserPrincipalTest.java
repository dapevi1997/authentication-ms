package co.com.crediya.api.security;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

class UserPrincipalTest {

    @Test
    @DisplayName("Constructor con todos los parámetros debe crear UserPrincipal correctamente")
    void allArgsConstructor_ShouldCreateUserPrincipalCorrectly() {
        // Arrange
        String email = "test@example.com";
        String password = "password123";
        Long idRole = 1L;
        String nameRole = "ROLE_USER";

        // Act
        UserPrincipal userPrincipal = new UserPrincipal(email, password, idRole, nameRole);

        // Assert
        assertThat(userPrincipal.getEmail()).isEqualTo(email);
        assertThat(userPrincipal.getPassword()).isEqualTo(password);
        assertThat(userPrincipal.getIdRole()).isEqualTo(idRole);
        assertThat(userPrincipal.getNameRole()).isEqualTo(nameRole);
    }

    @Test
    @DisplayName("Constructor por defecto debe crear UserPrincipal vacío")
    void noArgsConstructor_ShouldCreateEmptyUserPrincipal() {
        // Act
        UserPrincipal userPrincipal = new UserPrincipal();

        // Assert
        assertThat(userPrincipal.getEmail()).isNull();
        assertThat(userPrincipal.getPassword()).isNull();
        assertThat(userPrincipal.getIdRole()).isNull();
        assertThat(userPrincipal.getNameRole()).isNull();
    }

    @Test
    @DisplayName("Builder debe crear UserPrincipal correctamente")
    void builder_ShouldCreateUserPrincipalCorrectly() {
        // Arrange
        String email = "admin@example.com";
        String password = "admin123";
        Long idRole = 2L;
        String nameRole = "ROLE_ADMIN";

        // Act
        UserPrincipal userPrincipal = UserPrincipal.builder().email(email).password(password)
                .idRole(idRole).nameRole(nameRole).build();

        // Assert
        assertThat(userPrincipal.getEmail()).isEqualTo(email);
        assertThat(userPrincipal.getPassword()).isEqualTo(password);
        assertThat(userPrincipal.getIdRole()).isEqualTo(idRole);
        assertThat(userPrincipal.getNameRole()).isEqualTo(nameRole);
    }

    @Test
    @DisplayName("getAuthorities debe retornar SimpleGrantedAuthority con nameRole")
    void getAuthorities_ShouldReturnSimpleGrantedAuthorityWithNameRole() {
        // Arrange
        String nameRole = "ROLE_USER";
        UserPrincipal userPrincipal =
                UserPrincipal.builder().email("user@example.com").nameRole(nameRole).build();

        // Act
        Collection<? extends GrantedAuthority> authorities = userPrincipal.getAuthorities();

        // Assert
        assertThat(authorities).hasSize(1);
        assertThat(authorities.iterator().next()).isInstanceOf(SimpleGrantedAuthority.class)
                .extracting("role").isEqualTo(nameRole);
    }

    @Test
    @DisplayName("getUsername debe retornar email")
    void getUsername_ShouldReturnEmail() {
        // Arrange
        String email = "test@example.com";
        UserPrincipal userPrincipal = UserPrincipal.builder().email(email).build();

        // Act
        String username = userPrincipal.getUsername();

        // Assert
        assertThat(username).isEqualTo(email);
    }

    @Test
    @DisplayName("getPassword debe retornar password")
    void getPassword_ShouldReturnPassword() {
        // Arrange
        String password = "password123";
        UserPrincipal userPrincipal = UserPrincipal.builder().password(password).build();

        // Act
        String result = userPrincipal.getPassword();

        // Assert
        assertThat(result).isEqualTo(password);
    }

    @Test
    @DisplayName("UserPrincipal debe implementar UserDetails")
    void userPrincipal_ShouldImplementUserDetails() {
        // Arrange
        UserPrincipal userPrincipal = new UserPrincipal();

        // Assert
        assertThat(userPrincipal).isInstanceOf(UserDetails.class);
    }

    @Test
    @DisplayName("equals debe funcionar correctamente con objetos iguales")
    void equals_ShouldWorkCorrectlyWithEqualObjects() {
        // Arrange
        UserPrincipal userPrincipal1 = UserPrincipal.builder().email("test@example.com")
                .password("password123").idRole(1L).nameRole("ROLE_USER").build();

        UserPrincipal userPrincipal2 = UserPrincipal.builder().email("test@example.com")
                .password("password123").idRole(1L).nameRole("ROLE_USER").build();

        // Act & Assert
        assertThat(userPrincipal1).isEqualTo(userPrincipal2);
        assertThat(userPrincipal1.hashCode()).isEqualTo(userPrincipal2.hashCode());
    }

    @Test
    @DisplayName("equals debe funcionar correctamente con objetos diferentes")
    void equals_ShouldWorkCorrectlyWithDifferentObjects() {
        // Arrange
        UserPrincipal userPrincipal1 = UserPrincipal.builder().email("test1@example.com")
                .password("password123").idRole(1L).nameRole("ROLE_USER").build();

        UserPrincipal userPrincipal2 = UserPrincipal.builder().email("test2@example.com")
                .password("password123").idRole(1L).nameRole("ROLE_USER").build();

        // Act & Assert
        assertThat(userPrincipal1).isNotEqualTo(userPrincipal2);
    }

    @Test
    @DisplayName("toString debe generar representación string correcta")
    void toString_ShouldGenerateCorrectStringRepresentation() {
        // Arrange
        UserPrincipal userPrincipal = UserPrincipal.builder().email("test@example.com")
                .password("password123").idRole(1L).nameRole("ROLE_USER").build();

        // Act
        String result = userPrincipal.toString();

        // Assert
        assertThat(result).contains("test@example.com").contains("password123").contains("1")
                .contains("ROLE_USER");
    }

    @Test
    @DisplayName("setters deben modificar los valores correctamente")
    void setters_ShouldModifyValuesCorrectly() {
        // Arrange
        UserPrincipal userPrincipal = new UserPrincipal();

        // Act
        userPrincipal.setEmail("new@example.com");
        userPrincipal.setPassword("newPassword");
        userPrincipal.setIdRole(5L);
        userPrincipal.setNameRole("ROLE_ADMIN");

        // Assert
        assertThat(userPrincipal.getEmail()).isEqualTo("new@example.com");
        assertThat(userPrincipal.getPassword()).isEqualTo("newPassword");
        assertThat(userPrincipal.getIdRole()).isEqualTo(5L);
        assertThat(userPrincipal.getNameRole()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    @DisplayName("getAuthorities con diferentes roles debe funcionar correctamente")
    void getAuthorities_WithDifferentRoles_ShouldWorkCorrectly() {
        // Test con ROLE_ADMIN
        UserPrincipal adminUser = UserPrincipal.builder().nameRole("ROLE_ADMIN").build();

        Collection<? extends GrantedAuthority> adminAuthorities = adminUser.getAuthorities();
        assertThat(adminAuthorities).hasSize(1);
        assertThat(adminAuthorities.iterator().next().getAuthority()).isEqualTo("ROLE_ADMIN");

        // Test con ROLE_CLIENT
        UserPrincipal clientUser = UserPrincipal.builder().nameRole("ROLE_CLIENT").build();

        Collection<? extends GrantedAuthority> clientAuthorities = clientUser.getAuthorities();
        assertThat(clientAuthorities).hasSize(1);
        assertThat(clientAuthorities.iterator().next().getAuthority()).isEqualTo("ROLE_CLIENT");
    }

    @Test
    @DisplayName("UserPrincipal con valores extremos debe funcionar correctamente")
    void userPrincipal_WithExtremeValues_ShouldWorkCorrectly() {
        // Arrange
        String longEmail = "a".repeat(100) + "@example.com";
        String longPassword = "p".repeat(200);
        Long maxIdRole = Long.MAX_VALUE;
        String longNameRole = "ROLE_" + "X".repeat(50);

        // Act
        UserPrincipal userPrincipal = UserPrincipal.builder().email(longEmail)
                .password(longPassword).idRole(maxIdRole).nameRole(longNameRole).build();

        // Assert
        assertThat(userPrincipal.getEmail()).isEqualTo(longEmail);
        assertThat(userPrincipal.getPassword()).isEqualTo(longPassword);
        assertThat(userPrincipal.getIdRole()).isEqualTo(maxIdRole);
        assertThat(userPrincipal.getNameRole()).isEqualTo(longNameRole);
        assertThat(userPrincipal.getUsername()).isEqualTo(longEmail);
        assertThat(userPrincipal.getAuthorities()).hasSize(1);
    }
}
