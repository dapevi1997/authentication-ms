package co.com.crediya.r2dbc;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.gateways.UserRepository;
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
        return repository.save(co.com.crediya.r2dbc.helper.ObjectMapper.userToUserEntity(user))
                .map(co.com.crediya.r2dbc.helper.ObjectMapper::userEntityToUser)
                .as(transactionalOperator::transactional);
    }

    @Override
    public Mono<User> findUserByEmail(String email) {
        return repository.findByEmail(email).map(co.com.crediya.r2dbc.helper.ObjectMapper::userEntityToUser)
                .as(transactionalOperator::transactional);
    }
}
