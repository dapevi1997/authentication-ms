package co.com.crediya.r2dbc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.transaction.reactive.TransactionalOperator;
import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.values.Email;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class UserReactiveRepositoryAdapterTest {

        @InjectMocks
        UserRepositoryAdapter repositoryAdapter;

        @Mock
        UserReactiveRepository repository;

        @Mock
        ObjectMapper mapper;

        @Mock
        TransactionalOperator transactionalOperator;

        @Test
        void exitsByEmail() throws ConstructionDomainException {
                // Arrange
                String email = "mail@mail.com";
                when(repository.existsByEmail(email)).thenReturn(Mono.just(Boolean.TRUE));
                when(transactionalOperator.transactional(any(Mono.class)))
                                .thenAnswer(invocation -> invocation.getArgument(0));

                // Act
                Mono<Boolean> result = repositoryAdapter.existByEmail(new Email(email));

                // Arrange
                StepVerifier.create(result).expectNextMatches(exists -> exists.equals(Boolean.TRUE))
                                .verifyComplete();
        }

        @Test
        void mustSaveUser() throws ConstructionDomainException {
                /*
                 * // Arrange User user = new User(); user.setIdUser(new IdUser("1"));
                 * user.setName(new Name("Name")); user.setLastName(new LastName("Lastname"));
                 * user.setAddress(new Address("CR")); user.setBirthday(new Birthday("2020-03-03"));
                 * user.setBaseSalary(new BaseSalary("14")); user.setDocumentId(new
                 * DocumentId("14")); user.setPhone(new Phone("14")); user.setIdRole(new
                 * IdRole("2")); user.setEmail(new Email("mail@mail.com")); user.setPassword(new
                 * Password("Password1234*"));
                 * 
                 * UserEntity userEntity = new UserEntity(); userEntity.setIdUser(1L);
                 * userEntity.setName("Name"); userEntity.setLastname("Lastname");
                 * userEntity.setAddress("CR"); userEntity.setBirthdate(LocalDate.of(2020,12,3));
                 * userEntity.setBaseSalary(new BigDecimal(100));
                 * userEntity.setDocumentoIdentidad(14L); userEntity.setPhone(14L);
                 * userEntity.setIdRol(2L); userEntity.setEmail("mail@mail.com");
                 * userEntity.setPassword("Password1234*");
                 * 
                 * when(transactionalOperator.transactional(any(Mono.class))) .thenAnswer(invocation
                 * -> invocation.getArgument(0));
                 * 
                 * when(repository.save(any(UserEntity.class))).thenReturn(Mono.just(userEntity));
                 * when(mapper.map(any(UserEntity.class), any())).thenReturn(user);
                 * when(mapper.map(any(User.class), any())).thenReturn(userEntity);
                 * 
                 * Mono<User> result = repositoryAdapter.save(user);
                 * 
                 * StepVerifier.create(result) .expectNextMatches(savedUser ->
                 * savedUser.equals(user)) .verifyComplete();
                 */
        }
}
