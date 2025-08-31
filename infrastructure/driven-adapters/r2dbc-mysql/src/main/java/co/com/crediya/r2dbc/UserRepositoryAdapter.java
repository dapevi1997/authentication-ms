package co.com.crediya.r2dbc;

import co.com.crediya.model.role.Role;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.exception.DomainException;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.model.user.values.Email;
import co.com.crediya.r2dbc.entity.UserEntity;
import co.com.crediya.r2dbc.helper.CustomMapperR2dbc;
import co.com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UserRepositoryAdapter extends ReactiveAdapterOperations<
        User,
        UserEntity,
    Long,
        UserReactiveRepository
> implements UserRepository {
    private final TransactionalOperator transactionalOperator;
    private final CustomMapperR2dbc customMapperR2dbc;

    public UserRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper, TransactionalOperator transactionalOperator, CustomMapperR2dbc customMapperR2dbc) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, User.class));
        this.transactionalOperator = transactionalOperator;
        this.customMapperR2dbc = customMapperR2dbc;
    }

    @Override
    public Mono<User> save(User user){
        return repository.save(CustomMapperR2dbc.userToUserEntity(user))
                .flatMap(userEntity -> {
                    try {
                        return Mono.just(CustomMapperR2dbc.userEntityToUser(userEntity));
                    } catch (Exception e) {
                        return Mono.error(new Exception("Error al convertir UserEntity a User: " + e.getMessage()));
                    }
                })
                .as(transactionalOperator::transactional);
    }

    @Override
    public Mono<Boolean> existByEmail(Email email) {
        return repository.existsByEmail(email.getEmailUser())
                .as(transactionalOperator::transactional);
    }

    @Override
    public Mono<User> findByEmail(Email email) {
        return repository.findByEmail(email.getEmailUser())
                .flatMap(userEntity -> {
                    try {
                        return Mono.just(CustomMapperR2dbc.userEntityToUser(userEntity));
                    } catch (Exception e) {
                        return Mono.error(new Exception("Error al convertir UserEntity a User: " + e.getMessage()));
                    }
                })
                .as(transactionalOperator::transactional);
    }

    @Override
    public Flux<Role> findAllRoleByEmail(Email email) {
        return repository.findAllRolesByEmail(email.getEmailUser())
                .flatMap(roleEntity -> {
                    try {
                       return Flux.just(CustomMapperR2dbc.roleEntityToRole(roleEntity));
                    } catch (ConstructionDomainException e) {
                       return Mono.error(new DomainException("Error en convertir Rol Entity a Rol"));
                    }
                }).as(transactionalOperator::transactional);
    }
}
