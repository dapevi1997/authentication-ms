package co.com.crediya.usecase.getuserbyemail;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import reactor.core.publisher.Mono;

public class GetUserByEmailUseCase {
    private final UserRepository userRepository;
    public GetUserByEmailUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Mono<User> getUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }
}
