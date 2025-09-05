package co.com.crediya.r2dbc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import co.com.crediya.r2dbc.entity.RoleEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.reactive.TransactionalOperator;
import co.com.crediya.model.role.values.IdRole;
import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.exception.DomainException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class RoleRepositoryAdapterTest {

    @InjectMocks
    private RoleRepositoryAdapter roleRepositoryAdapter;

    @Mock
    private RoleReactiveRepository roleReactiveRepository;

    @Mock
    private TransactionalOperator transactionalOperator;

    private RoleEntity testRoleEntity;

    @BeforeEach
    void setUp() {
        testRoleEntity = new RoleEntity();
        testRoleEntity.setIdRole(1L);
        testRoleEntity.setName("ADMIN");
        testRoleEntity.setDescription("Administrator role");
    }

    @Test
    @DisplayName("Debería obtener todos los roles exitosamente")
    void deberiaObtenerTodosLosRolesExitosamente() {
        // Arrange
        when(roleReactiveRepository.findAll()).thenReturn(Flux.just(testRoleEntity));
        when(transactionalOperator.transactional(any(Flux.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act & Assert
        StepVerifier.create(roleRepositoryAdapter.getAllRoles()).assertNext(role -> {
            assertNotNull(role);
            assertNotNull(role.getIdRole());
            assertNotNull(role.getNameRole());
            assertNotNull(role.getDescriptionRole());
            assertEquals("ADMIN", role.getNameRole().getNameRole());
            assertEquals("Administrator role", role.getDescriptionRole().getDescriptionRole());
        }).verifyComplete();

        verify(roleReactiveRepository).findAll();
    }

    @Test
    @DisplayName("Debería retornar Flux vacío cuando no hay roles")
    void deberiaRetornarFluxVacioCuandoNoHayRoles() {
        // Arrange
        when(roleReactiveRepository.findAll()).thenReturn(Flux.empty());
        when(transactionalOperator.transactional(any(Flux.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act & Assert
        StepVerifier.create(roleRepositoryAdapter.getAllRoles()).verifyComplete();

        verify(roleReactiveRepository).findAll();
    }

    @Test
    @DisplayName("Debería encontrar rol por ID exitosamente")
    void deberiaEncontrarRolPorIdExitosamente() throws ConstructionDomainException {
        // Arrange
        IdRole idRole = new IdRole("1");
        when(roleReactiveRepository.findById(1L)).thenReturn(Mono.just(testRoleEntity));

        // Act & Assert
        StepVerifier.create(roleRepositoryAdapter.findById(idRole))
                .expectNextMatches(role -> role.getNameRole().getNameRole().equals("ADMIN"))
                .verifyComplete();

        verify(roleReactiveRepository).findById(1L);
    }

    @Test
    @DisplayName("Debería lanzar DomainException cuando rol no es encontrado")
    void deberiaLanzarDomainExceptionCuandoRolNoEsEncontrado() throws ConstructionDomainException {
        // Arrange
        IdRole idRole = new IdRole("999");
        when(roleReactiveRepository.findById(999L)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(roleRepositoryAdapter.findById(idRole))
                .expectError(DomainException.class).verify();

        verify(roleReactiveRepository).findById(999L);
    }

    @Test
    @DisplayName("Debería manejar ConstructionDomainException en getAllRoles")
    void deberiaManejarConstructionDomainExceptionEnGetAllRoles() {
        // Arrange
        RoleEntity invalidRoleEntity = new RoleEntity();
        invalidRoleEntity.setIdRole(1L);
        invalidRoleEntity.setName(null); // This will cause ConstructionDomainException
        invalidRoleEntity.setDescription("Description");

        when(roleReactiveRepository.findAll()).thenReturn(Flux.just(invalidRoleEntity));
        when(transactionalOperator.transactional(any(Flux.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act & Assert
        StepVerifier.create(roleRepositoryAdapter.getAllRoles())
                .expectError(ConstructionDomainException.class).verify();

        verify(roleReactiveRepository).findAll();
    }

    @Test
    @DisplayName("Debería manejar excepción en findById")
    void deberiaManejarExcepcionEnFindById() throws ConstructionDomainException {
        // Arrange
        IdRole idRole = new IdRole("1");
        RoleEntity invalidRoleEntity = new RoleEntity();
        invalidRoleEntity.setIdRole(1L);
        invalidRoleEntity.setName(null); // This will cause exception

        when(roleReactiveRepository.findById(1L)).thenReturn(Mono.just(invalidRoleEntity));

        // Act & Assert
        StepVerifier.create(roleRepositoryAdapter.findById(idRole))
                .expectError(DomainException.class).verify();

        verify(roleReactiveRepository).findById(1L);
    }
}
