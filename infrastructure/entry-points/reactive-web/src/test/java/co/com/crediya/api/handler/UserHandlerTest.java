package co.com.crediya.api.handler;

import co.com.crediya.api.dto.FindUserByEmailResponseDto;
import co.com.crediya.api.dto.RegisterUserRequestDto;
import co.com.crediya.api.exception.BadRequestException;
import co.com.crediya.api.util.CustomMapperWebFlux;
import co.com.crediya.model.logger.LoggerGateway;
import co.com.crediya.model.role.Role;
import co.com.crediya.model.role.values.IdRole;
import co.com.crediya.model.role.values.Name;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.exception.DomainException;
import co.com.crediya.model.user.values.Email;
import co.com.crediya.requestvalidator.RequestValidator;
import co.com.crediya.usecase.finduserbyemail.FindUserByEmailUseCase;
import co.com.crediya.usecase.getallroles.GetAllRolesUseCase;
import co.com.crediya.usecase.registeruser.RegisterUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserHandlerTest {

    @Mock
    private RequestValidator requestValidator;

    @Mock
    private RegisterUserUseCase registerUserUseCase;

    @Mock
    private GetAllRolesUseCase getAllRolesUseCase;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private CustomMapperWebFlux customMapper;

    @Mock
    private LoggerGateway loggerGateway;

    @Mock
    private FindUserByEmailUseCase findUserByEmailUseCase;

    private UserHandler userHandler;

    @BeforeEach
    void setUp() {
        userHandler = new UserHandler(
                requestValidator,
                registerUserUseCase,
                getAllRolesUseCase,
                objectMapper,
                customMapper,
                loggerGateway,
                findUserByEmailUseCase
        );
    }

    @Test
    @DisplayName("Registro de usuario exitoso debe retornar usuario registrado")
    void registerUser_Success_ShouldReturnRegisteredUser() throws ConstructionDomainException {
        // Arrange
        RegisterUserRequestDto requestDto = createValidRegisterUserRequestDto();
        User domainUser = createMockUser();
        Role mockRole = createMockRole();

        ServerRequest serverRequest = MockServerRequest.builder()
                .body(Mono.just(requestDto));

        when(objectMapper.map(any(), any())).thenReturn(requestDto);
        when(requestValidator.validate(any())).thenReturn(Collections.emptyList());
        when(customMapper.registerUserRequestDtoToUser(any())).thenReturn(domainUser);
        when(getAllRolesUseCase.getAllRoles()).thenReturn(Flux.just(mockRole));
        when(registerUserUseCase.registerUser(any())).thenReturn(Mono.just(domainUser));

        // Act
        Mono<ServerResponse> result = userHandler.registerUser(serverRequest);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(response -> response.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(requestValidator, times(1)).validate(any());
        verify(customMapper, times(1)).registerUserRequestDtoToUser(any());
        verify(getAllRolesUseCase, times(1)).getAllRoles();
        verify(registerUserUseCase, times(1)).registerUser(any());
    }

    @Test
    @DisplayName("Registro sin body debe retornar error")
    void registerUser_EmptyBody_ShouldReturnError() {
        // Arrange
        ServerRequest serverRequest = MockServerRequest.builder()
                .body(Mono.empty());

        // Act
        Mono<ServerResponse> result = userHandler.registerUser(serverRequest);

        // Assert
        StepVerifier.create(result)
                .expectError(BadRequestException.class)
                .verify();
    }

    @Test
    @DisplayName("Registro con datos inválidos debe retornar error")
    void registerUser_InvalidData_ShouldReturnError() {
        // Arrange
        RegisterUserRequestDto requestDto = createValidRegisterUserRequestDto();
        List<String> validationErrors = Arrays.asList("El campo nombre no puede estar vacío", "Email inválido");

        ServerRequest serverRequest = MockServerRequest.builder()
                .body(Mono.just(requestDto));

        when(objectMapper.map(any(), any())).thenReturn(requestDto);
        when(requestValidator.validate(any())).thenReturn(validationErrors);

        // Act
        Mono<ServerResponse> result = userHandler.registerUser(serverRequest);

        // Assert
        StepVerifier.create(result)
                .expectError(BadRequestException.class)
                .verify();

        verify(requestValidator, times(1)).validate(any());
    }

    @Test
    @DisplayName("Registro con error de construcción de dominio debe retornar error")
    void registerUser_DomainConstructionError_ShouldReturnError() throws ConstructionDomainException {
        // Arrange
        RegisterUserRequestDto requestDto = createValidRegisterUserRequestDto();

        ServerRequest serverRequest = MockServerRequest.builder()
                .body(Mono.just(requestDto));

        when(objectMapper.map(any(), any())).thenReturn(requestDto);
        when(requestValidator.validate(any())).thenReturn(Collections.emptyList());
        when(customMapper.registerUserRequestDtoToUser(any()))
                .thenThrow(new ConstructionDomainException("Error al construir el Usuario del dominio"));

        // Act
        Mono<ServerResponse> result = userHandler.registerUser(serverRequest);

        // Assert
        StepVerifier.create(result)
                .expectError(ConstructionDomainException.class)
                .verify();

        verify(requestValidator, times(1)).validate(any());
        verify(customMapper, times(1)).registerUserRequestDtoToUser(any());
    }

    @Test
    @DisplayName("Registro con rol inexistente debe retornar error")
    void registerUser_NonExistentRole_ShouldReturnError() throws ConstructionDomainException {
        // Arrange
        RegisterUserRequestDto requestDto = createValidRegisterUserRequestDto();
        User domainUser = createMockUser();

        ServerRequest serverRequest = MockServerRequest.builder()
                .body(Mono.just(requestDto));

        when(objectMapper.map(any(), any())).thenReturn(requestDto);
        when(requestValidator.validate(any())).thenReturn(Collections.emptyList());
        when(customMapper.registerUserRequestDtoToUser(any())).thenReturn(domainUser);
        when(getAllRolesUseCase.getAllRoles()).thenReturn(Flux.empty()); // No roles encontrados

        // Act
        Mono<ServerResponse> result = userHandler.registerUser(serverRequest);

        // Assert
        StepVerifier.create(result)
                .expectError(DomainException.class)
                .verify();

        verify(requestValidator, times(1)).validate(any());
        verify(customMapper, times(1)).registerUserRequestDtoToUser(any());
        verify(getAllRolesUseCase, times(1)).getAllRoles();
    }

    @Test
    @DisplayName("Registro con rol diferente debe retornar error")
    void registerUser_DifferentRole_ShouldReturnError() throws ConstructionDomainException {
        // Arrange
        RegisterUserRequestDto requestDto = createValidRegisterUserRequestDto();
        User domainUser = createMockUser();
        Role differentRole = createMockRoleWithDifferentId();

        ServerRequest serverRequest = MockServerRequest.builder()
                .body(Mono.just(requestDto));

        when(objectMapper.map(any(), any())).thenReturn(requestDto);
        when(requestValidator.validate(any())).thenReturn(Collections.emptyList());
        when(customMapper.registerUserRequestDtoToUser(any())).thenReturn(domainUser);
        when(getAllRolesUseCase.getAllRoles()).thenReturn(Flux.just(differentRole));

        // Act
        Mono<ServerResponse> result = userHandler.registerUser(serverRequest);

        // Assert
        StepVerifier.create(result)
                .expectError(DomainException.class)
                .verify();

        verify(requestValidator, times(1)).validate(any());
        verify(customMapper, times(1)).registerUserRequestDtoToUser(any());
        verify(getAllRolesUseCase, times(1)).getAllRoles();
    }

    @Test
    @DisplayName("Buscar usuario por email exitoso debe retornar usuario")
    void findUserByEmail_Success_ShouldReturnUser() {
        // Arrange
        String email = "test@mail.com";
        User domainUser = createMockUser();
        FindUserByEmailResponseDto responseDto = createMockFindUserByEmailResponseDto();

        ServerRequest serverRequest = MockServerRequest.builder()
                .queryParam("email", email)
                .build();

        when(findUserByEmailUseCase.findUserByEmail(any(String.class))).thenReturn(Mono.just(domainUser));
        when(customMapper.userToFindUSerByEmailDto(any())).thenReturn(responseDto);

        // Act
        Mono<ServerResponse> result = userHandler.findUserByEmail(serverRequest);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(response -> response.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(findUserByEmailUseCase, times(1)).findUserByEmail(anyString());
        verify(customMapper, times(1)).userToFindUSerByEmailDto(any());
    }

    @Test
    @DisplayName("Buscar usuario sin email debe retornar error")
    void findUserByEmail_MissingEmail_ShouldReturnError() {
        // Arrange
        ServerRequest serverRequest = MockServerRequest.builder().build();

        // Act
        Mono<ServerResponse> result = userHandler.findUserByEmail(serverRequest);

        // Assert
        StepVerifier.create(result)
                .expectError(BadRequestException.class)
                .verify();
    }

    @Test
    @DisplayName("Buscar usuario con error en el caso de uso debe manejar error")
    void findUserByEmail_UseCaseError_ShouldHandleError() {
        // Arrange
        String email = "test@mail.com";

        ServerRequest serverRequest = MockServerRequest.builder()
                .queryParam("email", email)
                .build();

        when(findUserByEmailUseCase.findUserByEmail(any(String.class)))
                .thenReturn(Mono.error(new DomainException("Usuario no encontrado")));

        // Act
        Mono<ServerResponse> result = userHandler.findUserByEmail(serverRequest);

        // Assert
        StepVerifier.create(result)
                .expectError(DomainException.class)
                .verify();

        verify(findUserByEmailUseCase, times(1)).findUserByEmail(anyString());
    }

    @Test
    @DisplayName("Registro con error en el caso de uso debe manejar error")
    void registerUser_UseCaseError_ShouldHandleError() throws ConstructionDomainException {
        // Arrange
        RegisterUserRequestDto requestDto = createValidRegisterUserRequestDto();
        User domainUser = createMockUser();
        Role mockRole = createMockRole();

        ServerRequest serverRequest = MockServerRequest.builder()
                .body(Mono.just(requestDto));

        when(objectMapper.map(any(), any())).thenReturn(requestDto);
        when(requestValidator.validate(any())).thenReturn(Collections.emptyList());
        when(customMapper.registerUserRequestDtoToUser(any())).thenReturn(domainUser);
        when(getAllRolesUseCase.getAllRoles()).thenReturn(Flux.just(mockRole));
        when(registerUserUseCase.registerUser(any()))
                .thenReturn(Mono.error(new DomainException("Error al registrar usuario")));

        // Act
        Mono<ServerResponse> result = userHandler.registerUser(serverRequest);

        // Assert
        StepVerifier.create(result)
                .expectError(DomainException.class)
                .verify();

        verify(registerUserUseCase, times(1)).registerUser(any());
    }

    private RegisterUserRequestDto createValidRegisterUserRequestDto() {
        return RegisterUserRequestDto.builder()
                .name("Juan")
                .lastName("Pérez")
                .email("juan.perez@mail.com")
                .password("password123")
                .address("Calle 123 #45-67")
                .documentId("12345678")
                .phone("3001234567")
                .userBirthday("1990-01-01")
                .baseSalary("2500000")
                .idRole("1")
                .build();
    }

    private User createMockUser() {
        User user = new User();
        try {
            user.setIdUser(new co.com.crediya.model.user.values.IdUser("1"));
            user.setEmail(new Email("juan.perez@mail.com"));
            user.setIdRole(new co.com.crediya.model.user.values.IdRole("1"));
            user.setName(new co.com.crediya.model.user.values.Name("Juan"));
            user.setBaseSalary(new co.com.crediya.model.user.values.BaseSalary("2500000"));
        } catch (Exception e) {
            // Manejo de excepción para el mock
        }
        return user;
    }

    private Role createMockRole() {
        Role role = new Role();
        try {
            role.setIdRole(new IdRole("1"));
            role.setNameRole(new Name("ADMIN"));
        } catch (Exception e) {
            // Manejo de excepción para el mock
        }
        return role;
    }

    private Role createMockRoleWithDifferentId() {
        Role role = new Role();
        try {
            role.setIdRole(new IdRole("2")); // ID diferente
            role.setNameRole(new Name("CLIENT"));
        } catch (Exception e) {
            // Manejo de excepción para el mock
        }
        return role;
    }

    private FindUserByEmailResponseDto createMockFindUserByEmailResponseDto() {
        return FindUserByEmailResponseDto.builder()
                .idUser(1L)
                .name("Juan")
                .lastName("Pérez")
                .email("juan.perez@mail.com")
                .documentId("12345678")
                .phone("3001234567")
                .address("Calle 123 #45-67")
                .birthday(LocalDate.of(1990, 1, 1))
                .baseSalary(new BigDecimal("2500000"))
                .idRole(1L)
                .build();
    }
}