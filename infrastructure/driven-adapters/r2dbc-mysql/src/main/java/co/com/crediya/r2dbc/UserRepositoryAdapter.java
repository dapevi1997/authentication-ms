package co.com.crediya.r2dbc;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.model.user.values.Email;
import co.com.crediya.r2dbc.helper.CustomMapperR2dbc;
import co.com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Repository
public class UserRepositoryAdapter extends ReactiveAdapterOperations<
        User,
    UserEntity,
    Long,
        UserReactiveRepository
> implements UserRepository {
    private final TransactionalOperator transactionalOperator;

    public UserRepositoryAdapter(UserReactiveRepository repository, ObjectMapper mapper, TransactionalOperator transactionalOperator) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, User.class));
        this.transactionalOperator = transactionalOperator;
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
}
