package co.com.crediya.usecase.finduserbyemail;

import co.com.crediya.model.logger.LoggerGateway;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.model.user.values.Email;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindUserByEmailUseCaseTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private LoggerGateway loggerGateway;
    @InjectMocks
    private FindUserByEmailUseCase useCase;

    @Test
    void testFindByEmail() throws ConstructionDomainException {
        // Arrange
        String email = "mail@mail.com";
        User user = new User();

        when(userRepository.findByEmail(any())).thenReturn(Mono.just(user));

        // Act & Assert
        StepVerifier.create(useCase.findUserByEmail(email))
                .assertNext(foundUser -> assertEquals(user, foundUser))
                .verifyComplete();
    }

}