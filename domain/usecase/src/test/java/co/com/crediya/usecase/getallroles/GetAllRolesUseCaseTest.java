package co.com.crediya.usecase.getallroles;

import co.com.crediya.model.role.Role;
import co.com.crediya.model.role.gateways.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetAllRolesUseCaseTest {
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private GetAllRolesUseCase useCase;

    @Test
    void getAllRoles_success() {
        Role role1 = mock(Role.class);
        Role role2 = mock(Role.class);
        when(roleRepository.getAllRoles()).thenReturn(Flux.just(role1, role2));

        StepVerifier.create(useCase.getAllRoles()).expectNext(role1).expectNext(role2)
                .verifyComplete();
    }

    @Test
    void getAllRoles_empty() {
        when(roleRepository.getAllRoles()).thenReturn(Flux.empty());

        StepVerifier.create(useCase.getAllRoles()).verifyComplete();
    }

    @Test
    void getAllRoles_error() {
        when(roleRepository.getAllRoles()).thenReturn(Flux.error(new RuntimeException("DB error")));

        StepVerifier.create(useCase.getAllRoles()).expectError(RuntimeException.class).verify();
    }
}
