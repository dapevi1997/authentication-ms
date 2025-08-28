package co.com.crediya.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.Collections;

import co.com.crediya.model.role.Role;
import co.com.crediya.model.role.values.Description;
import co.com.crediya.model.role.values.IdRole;
import co.com.crediya.model.role.values.Name;
import co.com.crediya.model.user.values.IdUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import co.com.crediya.api.dto.RegisterUserRequestDto;
import co.com.crediya.api.exception.GlobalExceptionHandler;
import co.com.crediya.api.exception.OtherBeans;
import co.com.crediya.api.util.CustomMapperWebFlux;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.requestvalidator.RequestValidator;
import co.com.crediya.usecase.getallroles.GetAllRolesUseCase;
import co.com.crediya.usecase.registeruser.RegisterUserUseCase;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest
@ContextConfiguration(classes = {RouterRest.class, Handler.class, RouterRestTest.TestConfig.class,
        GlobalExceptionHandler.class, OtherBeans.class})
class RouterRestTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private RequestValidator requestValidator;

    @Autowired
    private RegisterUserUseCase registerUserUseCase;

    @Autowired
    private GetAllRolesUseCase getAllRolesUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomMapperWebFlux customMapper;

    private RegisterUserRequestDto validRequestDto;

    @Configuration
    static class TestConfig {

        @Bean
        @Primary
        public RequestValidator requestValidator() {
            return Mockito.mock(RequestValidator.class);
        }

        @Bean
        @Primary
        public RegisterUserUseCase registerUserUseCase() {
            return Mockito.mock(RegisterUserUseCase.class);
        }

        @Bean
        @Primary
        public GetAllRolesUseCase getAllRolesUseCase() {
            return Mockito.mock(GetAllRolesUseCase.class);
        }

        @Bean
        @Primary
        public ObjectMapper objectMapper() {
            return Mockito.mock(ObjectMapper.class);
        }

        @Bean
        @Primary
        public CustomMapperWebFlux customMapperWebFlux() {
            return Mockito.mock(CustomMapperWebFlux.class);
        }
    }

    @BeforeEach
    void setUp() {
        validRequestDto = new RegisterUserRequestDto();
        validRequestDto.setName("Juan");
        validRequestDto.setLastName("Perez");
        validRequestDto.setEmail("juan@test.com");
        validRequestDto.setUserBirthday("1990-01-01");
        validRequestDto.setAddress("Calle 123");
        validRequestDto.setDocumentId("12345678");
        validRequestDto.setBaseSalary("1000.00");
        validRequestDto.setIdRole("1");
        validRequestDto.setPhone("3001234567");

        // Reset mocks before each test
        Mockito.reset(requestValidator, registerUserUseCase, getAllRolesUseCase, objectMapper,
                customMapper);
    }

    @Test
    void testRegisterUserEndpointWithValidRequest() throws ConstructionDomainException {
        // Arrange
        User mockUser = new User();
        mockUser.setIdRole(new co.com.crediya.model.user.values.IdRole("1"));
        mockUser.setIdUser(new IdUser("1"));
        Role mockRole = new Role();
        mockRole.setIdRole(new IdRole("1"));
        mockRole.setDescriptionRole(new Description("descripcion"));
        mockRole.setNameRole(new Name("name"));

        when(requestValidator.validate(any())).thenReturn(Collections.emptyList());
        when(customMapper.registerUserRequestDtoToUser(any())).thenReturn(mockUser);
        when(getAllRolesUseCase.getAllRoles()).thenReturn(Flux.just(mockRole));
        when(registerUserUseCase.registerUser(any())).thenReturn(Mono.just(mockUser));

        // Act and Assert
        webTestClient.post().uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(validRequestDto).exchange()
                .expectStatus().isOk();
    }

    @Test
    void testRegisterUserEndpointWithEmptyBody() {
        // When & Then
        webTestClient.post().uri("/api/v1/usuarios").contentType(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isBadRequest();
    }

    @Test
    void testRegisterUserEndpointWithInvalidJson() {
        // When & Then
        webTestClient.post().uri("/api/v1/usuarios").contentType(MediaType.APPLICATION_JSON)
                .bodyValue("{invalid json}").exchange().expectStatus().isBadRequest();
    }
}
