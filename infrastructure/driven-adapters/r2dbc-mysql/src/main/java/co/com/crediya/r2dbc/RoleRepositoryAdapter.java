package co.com.crediya.r2dbc;


import co.com.crediya.model.role.Role;
import co.com.crediya.model.role.gateways.RoleRepository;
import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.r2dbc.helper.CustomMapperR2dbc;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;

@Repository
public class RoleRepositoryAdapter implements RoleRepository {
    private final RoleReactiveRepository roleReactiveRepository;
    private final TransactionalOperator transactionalOperator;

    public RoleRepositoryAdapter(RoleReactiveRepository roleReactiveRepository, TransactionalOperator transactionalOperator) {
        this.roleReactiveRepository = roleReactiveRepository;
        this.transactionalOperator = transactionalOperator;
    }

    @Override
    public Flux<Role> getAllRoles() {
        return roleReactiveRepository.findAll()
                .flatMap(roleEntity -> {
                    try {
                        return Flux.just(CustomMapperR2dbc.roleEntityToRole(roleEntity));
                    } catch (ConstructionDomainException e) {
                        return Flux.error(e);
                    }
                })
                .as(transactionalOperator::transactional);
    }
}
