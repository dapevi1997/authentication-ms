package co.com.crediya.usecase.registeruser;

import co.com.crediya.model.logger.LoggerGateway;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.DomainException;
import co.com.crediya.model.user.gateways.UserRepository;
import reactor.core.publisher.Mono;

public class RegisterUserUseCase {
    private final UserRepository userRepository;
    private final LoggerGateway loggerGateway;

    public RegisterUserUseCase(UserRepository userRepository, LoggerGateway loggerGateway) {
        this.userRepository = userRepository;
        this.loggerGateway = loggerGateway;
    }

    public Mono<User> registerUser(User user) {
        return userRepository.existByEmail(user.getEmail())
                .flatMap(exits -> {
                    if (exits){
                        loggerGateway.error("Ya existe un usuario con email {}", user.getEmail().getEmailUser());
                        return Mono.error(new DomainException("Ya existe un usuario con email " + user.getEmail().getEmailUser()));
                    }
                    return userRepository.save(user);
                })
                .doOnSuccess(savedUser -> loggerGateway.info("Usuario registrado exitosamente con id {}", savedUser.getIdUser().toString()))
                .doOnError(error -> loggerGateway.error("Error en registro de usuario con email {}. Mensaje: {}", user.getEmail().getEmailUser(), error.getMessage()));
    }
}
