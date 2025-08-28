package co.com.crediya.usecase.registeruser;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.DomainException;
import co.com.crediya.model.user.gateways.UserRepository;
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
    @InjectMocks
    private RegisterUserUseCase useCase;

    @Test
    void registerUserEmailExists() {
        User user = mock(User.class);
        when(user.getEmail()).thenReturn(mock(co.com.crediya.model.user.values.Email.class));
        when(user.getEmail().getEmailUser()).thenReturn("test@mail.com");
        when(userRepository.existByEmail(any())).thenReturn(Mono.just(true));

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
