package co.com.crediya.model.role.gateways;

import co.com.crediya.model.role.Role;
import reactor.core.publisher.Flux;

public interface RoleRepository {
    Flux<Role> getAllRoles();
}
