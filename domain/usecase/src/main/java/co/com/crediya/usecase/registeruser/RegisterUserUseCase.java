package co.com.crediya.usecase.registeruser;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.UserContructionException;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.model.user.values.Email;
import reactor.core.publisher.Mono;

public class RegisterUserUseCase {
    private final UserRepository userRepository;

    public RegisterUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<User> registerUser(User user) {
        return userRepository.save(user);}
}
