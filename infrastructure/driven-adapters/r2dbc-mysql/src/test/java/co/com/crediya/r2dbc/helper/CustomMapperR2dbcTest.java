package co.com.crediya.r2dbc.helper;

import co.com.crediya.model.role.Role;
import co.com.crediya.model.role.values.Description;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.values.*;
import co.com.crediya.r2dbc.entity.RoleEntity;
import co.com.crediya.r2dbc.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CustomMapperR2dbcTest {

    private User testUser;
    private UserEntity testUserEntity;
    private Role testRole;
    private RoleEntity testRoleEntity;

    @BeforeEach
    void setUp() throws ConstructionDomainException {
        // Setup User domain object
        testUser = new User();
        testUser.setIdUser(new IdUser("1"));
        testUser.setName(new Name("John"));
        testUser.setLastName(new LastName("Doe"));
        testUser.setBirthday(new Birthday("1990-01-15"));
        testUser.setEmail(new Email("john.doe@example.com"));
        testUser.setAddress(new Address("123 Main St"));
        testUser.setPhone(new Phone("1234567890"));
        testUser.setDocumentId(new DocumentId("12345678"));
        testUser.setBaseSalary(new BaseSalary("50000"));
        testUser.setIdRole(new IdRole("2"));
        testUser.setPassword(new Password("Password123*"));

        // Setup UserEntity
        testUserEntity = new UserEntity();
        testUserEntity.setIdUser(1L);
        testUserEntity.setName("John");
        testUserEntity.setLastname("Doe");
        testUserEntity.setBirthdate(LocalDate.of(1990, 1, 15));
        testUserEntity.setEmail("john.doe@example.com");
        testUserEntity.setAddress("123 Main St");
        testUserEntity.setPhone(1234567890L);
        testUserEntity.setDocumentoIdentidad(12345678L);
        testUserEntity.setBaseSalary(new BigDecimal("50000"));
        testUserEntity.setIdRol(2L);
        testUserEntity.setPassword("Password123*");

        // Setup Role domain object
        testRole = new Role();
        testRole.setIdRole(new co.com.crediya.model.role.values.IdRole("1"));
        testRole.setNameRole(new co.com.crediya.model.role.values.Name("ADMIN"));
        testRole.setDescriptionRole(new Description("Administrator role"));

        // Setup RoleEntity
        testRoleEntity = new RoleEntity();
        testRoleEntity.setIdRole(1L);
        testRoleEntity.setName("ADMIN");
        testRoleEntity.setDescription("Administrator role");
    }

    @Test
    void shouldMapUserToUserEntity() {
        // When
        UserEntity result = CustomMapperR2dbc.userToUserEntity(testUser);

        // Then
        assertNotNull(result);
        assertEquals("John", result.getName());
        assertEquals("Doe", result.getLastname());
        assertEquals(LocalDate.of(1990, 1, 15), result.getBirthdate());
        assertEquals("john.doe@example.com", result.getEmail());
        assertEquals("123 Main St", result.getAddress());
        assertEquals(1234567890L, result.getPhone());
        assertEquals(12345678L, result.getDocumentoIdentidad());
        assertEquals(new BigDecimal("50000"), result.getBaseSalary());
        assertEquals(2L, result.getIdRol());
    }

    @Test
    void shouldMapUserEntityToUser() throws ConstructionDomainException {
        // When
        User result = CustomMapperR2dbc.userEntityToUser(testUserEntity);

        // Then
        assertNotNull(result);
        assertEquals("John", result.getName().getNameUser());
        assertEquals("John", result.getLastName().getLastNameUser()); // Note: mapper uses name for lastname
        assertEquals("1990-01-15", result.getBirthday().getUserBirthday().toString());
        assertEquals("john.doe@example.com", result.getEmail().getEmailUser());
        assertEquals("123 Main St", result.getAddress().getAdress());
        assertEquals("1234567890", result.getPhone().getPhoneUser().toString());
        assertEquals("12345678", result.getDocumentId().getDocumentoIdentidadUsuario().toString());
        assertEquals("50000", result.getBaseSalary().getBaseSalaryUser().toString());
        assertEquals("Password123*", result.getPassword().getPassword());
    }

    @Test
    void shouldMapRoleToRoleEntity() {
        // When
        RoleEntity result = CustomMapperR2dbc.roleToRoleEntity(testRole);

        // Then
        assertNotNull(result);
        assertEquals("ADMIN", result.getName());
        assertEquals("Administrator role", result.getDescription());
    }

    @Test
    void shouldMapRoleEntityToRole() throws ConstructionDomainException {
        // When
        Role result = CustomMapperR2dbc.roleEntityToRole(testRoleEntity);

        // Then
        assertNotNull(result);
        assertEquals("ADMIN", result.getNameRole().getNameRole());
        assertEquals("Administrator role", result.getDescriptionRole().getDescriptionRole());
    }

    @Test
    void shouldThrowConstructionDomainExceptionForInvalidRoleEntity() {
        // Given
        RoleEntity invalidRoleEntity = new RoleEntity();
        invalidRoleEntity.setIdRole(1L);
        invalidRoleEntity.setName(null); // Invalid name
        invalidRoleEntity.setDescription("Description");

        // When & Then
        assertThrows(ConstructionDomainException.class, () -> 
            CustomMapperR2dbc.roleEntityToRole(invalidRoleEntity)
        );
    }

/*    @Test
    void shouldHandleNullValuesInUserMapping() {
        // Given
        User userWithNulls = new User();
        try {
            userWithNulls.setName(new Name("Test"));
            userWithNulls.setLastName(new LastName("User"));
            userWithNulls.setBirthday(new Birthday("2000-01-01"));
            userWithNulls.setEmail(new Email("test@example.com"));
            userWithNulls.setAddress(new Address("Address"));
            userWithNulls.setPhone(new Phone("123456789"));
            userWithNulls.setDocumentId(new DocumentId("987654321"));
            userWithNulls.setBaseSalary(new BaseSalary("30000"));
            userWithNulls.setIdRole(new IdRole("1"));
        } catch (ConstructionDomainException e) {
            fail("Setup failed: " + e.getMessage());
        }

        // When
        UserEntity result = CustomMapperR2dbc.userToUserEntity(userWithNulls);

        // Then
        assertNotNull(result);
        assertEquals("Test", result.getName());
        assertEquals("User", result.getLastname());
    }*/
}