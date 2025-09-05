package co.com.crediya.usecase.finduserbyemail;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.model.user.values.Email;
import reactor.core.publisher.Mono;

public class FindUserByEmailUseCase {
    private final UserRepository userRepository;

    public FindUserByEmailUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Mono<User> findUserByEmail(String email) {
        return Mono.fromCallable(() -> new Email(email))
                .flatMap(userRepository::findByEmail);
    }
}
