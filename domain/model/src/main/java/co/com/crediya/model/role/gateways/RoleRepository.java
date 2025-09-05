package co.com.crediya.model.role.gateways;

import co.com.crediya.model.role.Role;
import co.com.crediya.model.role.values.IdRole;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleRepository {
    Flux<Role> getAllRoles();
    Mono<Role> findById(IdRole id);
}
