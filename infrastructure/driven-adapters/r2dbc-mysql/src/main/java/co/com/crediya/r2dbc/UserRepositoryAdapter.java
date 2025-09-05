package co.com.crediya.r2dbc;

import co.com.crediya.model.role.Role;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.model.user.values.CreatedAt;
import co.com.crediya.model.user.values.Email;
import co.com.crediya.model.user.values.Password;
import co.com.crediya.r2dbc.entity.UserEntity;
import co.com.crediya.r2dbc.helper.CustomMapperR2dbc;
import co.com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Repository
public class UserRepositoryAdapter extends ReactiveAdapterOperations<
        User,
        UserEntity,
    Long,
        UserReactiveRepository
> implements UserRepository {
    private final TransactionalOperator transactionalOperator;
    private final PasswordEncoder passwordEncoder;

    public UserRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper, TransactionalOperator transactionalOperator, PasswordEncoder passwordEncoder) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, User.class));
        this.transactionalOperator = transactionalOperator;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<User> save(User user) {
        return Mono.just(user)
                .flatMap(u -> Mono.fromCallable(() -> {
                    u.setPassword(new Password(passwordEncoder.encode(u.getPassword().getPassword())));
                    u.setCreatedAt(new CreatedAt(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
                    return u;
                }))
                .map(CustomMapperR2dbc::userToUserEntity)
                .flatMap(repository::save)
                .flatMap(userEntity -> Mono.fromCallable(() -> CustomMapperR2dbc.userEntityToUser(userEntity)))
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
                .flatMap(userEntity ->
                        Mono.fromCallable(() -> CustomMapperR2dbc.userEntityToUser(userEntity))
                )
                .as(transactionalOperator::transactional);
    }

    @Override
    public Flux<Role> findAllRoleByEmail(Email email) {
        return repository.findAllRolesByEmail(email.getEmailUser())
                .flatMap(roleEntity -> Mono.fromCallable(() -> CustomMapperR2dbc.roleEntityToRole(roleEntity)))
                .as(transactionalOperator::transactional);
    }
}
