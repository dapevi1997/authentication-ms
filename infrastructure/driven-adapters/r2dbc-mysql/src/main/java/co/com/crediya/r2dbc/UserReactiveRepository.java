package co.com.crediya.r2dbc;

import co.com.crediya.r2dbc.entity.RoleEntity;
import co.com.crediya.r2dbc.entity.UserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserReactiveRepository extends ReactiveCrudRepository<UserEntity, Long>, ReactiveQueryByExampleExecutor<UserEntity> {
    Mono<Boolean> existsByEmail(String email);

    Mono<UserEntity> findByEmail(String email);

    @Query("""
                SELECT r.id_role, r.name, r.description
                FROM role r
                INNER JOIN user u ON r.id_role = u.id_role
                WHERE u.email = :email
            """)
    Flux<RoleEntity> findAllRolesByEmail(String email);

    @Query("""
                SELECT u.*
                FROM user u
                INNER JOIN role r ON u.id_role = r.id_role
                WHERE r.name = :roleName
            """)
    Flux<UserEntity> findByRoleName(String roleName);
}
