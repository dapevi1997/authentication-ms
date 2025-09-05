package co.com.crediya.model.user.gateways;

import co.com.crediya.model.role.Role;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.values.Email;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> save(User user);
    Mono<Boolean> existByEmail(Email email);
    Mono<User> findByEmail(Email email);
    Flux<Role> findAllRoleByEmail(Email email);
}
