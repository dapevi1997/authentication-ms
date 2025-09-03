package co.com.crediya.usecase.registeruser;

import co.com.crediya.model.logger.LoggerGateway;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.exception.DomainException;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.model.user.values.Email;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUserUseCaseTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private LoggerGateway loggerGateway;
    @InjectMocks
    private RegisterUserUseCase useCase;

    @Test
    void registerUserEmailExists() throws ConstructionDomainException {
        User user = new User();
        Email email = new Email("test@mail.com");
        user.setEmail(email);

        when(userRepository.existByEmail(any())).thenReturn(Mono.just(true));
        when(userRepository.save(any(User.class))).thenReturn(Mono.just(user));

        StepVerifier.create(useCase.registerUser(user)).expectError(DomainException.class).verify();
    }

    @Test
    void registerUserSaveError() {
        User user = mock(User.class);
        when(userRepository.existByEmail(any())).thenReturn(Mono.just(false));
        when(userRepository.save(any())).thenReturn(Mono.error(new RuntimeException("DB error")));

        StepVerifier.create(useCase.registerUser(user)).expectError(RuntimeException.class)
                .verify();
    }
}
