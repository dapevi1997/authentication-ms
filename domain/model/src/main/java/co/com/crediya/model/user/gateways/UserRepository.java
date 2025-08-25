package co.com.crediya.model.user.gateways;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.values.Email;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> save(User user);
    Mono<Boolean> existByEmail(Email email);
}
