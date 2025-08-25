package co.com.crediya.usecase.registeruser;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.DomainException;
import co.com.crediya.model.user.gateways.UserRepository;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

public class RegisterUserUseCase {
    private final UserRepository userRepository;

    private static final Logger logger = Logger.getLogger(RegisterUserUseCase.class.getName());

    public RegisterUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> registerUser(User user) {
        return userRepository.existByEmail(user.getEmail())
                .flatMap(exits -> {
                    if (exits){
                        logger.warning("Ya existe un usuario con email " + user.getEmail().getEmailUser() + " existe.");
                        return Mono.error(new DomainException("Ya existe un usuario con email " + user.getEmail().getEmailUser()));
                    }
                    return userRepository.save(user);
                })
                .doOnSuccess(savedUser -> logger.info("Usuario registrado exitosamente con id " + savedUser.getIdUser().toString()))
                .doOnError(error -> logger.severe("Error en registro de usuario con email" + user.getEmail().getEmailUser() + " Mensaje: " + error.getMessage()));
    }
}
