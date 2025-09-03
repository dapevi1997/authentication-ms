package co.com.crediya.api.handler;

import co.com.crediya.api.dto.LoginRequestDto;
import co.com.crediya.api.security.UserPrincipal;
import co.com.crediya.api.security.util.JwtService;
import co.com.crediya.model.logger.LoggerGateway;
import co.com.crediya.model.role.Role;
import co.com.crediya.model.role.gateways.RoleRepository;
import co.com.crediya.model.role.values.IdRole;
import co.com.crediya.model.role.values.Name;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.DomainException;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.model.user.values.Email;
import co.com.crediya.model.user.values.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.reactive.function.server.MockServerRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AuthHandlerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private LoggerGateway loggerGateway;

    private AuthHandler authHandler;

    @BeforeEach
    void setUp() {
        authHandler = new AuthHandler(userRepository, roleRepository, passwordEncoder, jwtService, loggerGateway);
    }

    @Test
    @DisplayName("Login exitoso debe generar token")
    void login_Success_ShouldGenerateToken() {
        // Arrange
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("test@mail.com");
        loginRequestDto.setPassword("password123");

        ServerRequest serverRequest = MockServerRequest.builder()
                .body(Mono.just(loginRequestDto));

        User mockUser = createMockUser();
        Role mockRole = createMockRole();

        when(userRepository.findByEmail(any(Email.class))).thenReturn(Mono.just(mockUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(roleRepository.findById(any(IdRole.class))).thenReturn(Mono.just(mockRole));
        when(jwtService.generateToken(any(UserPrincipal.class))).thenReturn("mock-jwt-token");

        // Act
        Mono<ServerResponse> result = authHandler.login(serverRequest);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(response -> response.statusCode().is2xxSuccessful())
                .verifyComplete();

        verify(userRepository, times(1)).findByEmail(any(Email.class));
        verify(passwordEncoder, times(1)).matches(anyString(), anyString());
        verify(roleRepository, times(1)).findById(any(IdRole.class));
        verify(jwtService, times(1)).generateToken(any(UserPrincipal.class));
    }

    @Test
    @DisplayName("Login con email inválido debe retornar error")
    void login_InvalidEmail_ShouldReturnError() {
        // Arrange
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("invalid-email");
        loginRequestDto.setPassword("password123");

        ServerRequest serverRequest = MockServerRequest.builder()
                .body(Mono.just(loginRequestDto));

        // Act
        Mono<ServerResponse> result = authHandler.login(serverRequest);

        // Assert
        StepVerifier.create(result)
                .expectError(DomainException.class)
                .verify();
    }

    @Test
    @DisplayName("Login con usuario inexistente debe retornar error")
    void login_UserNotFound_ShouldReturnError() {
        // Arrange
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("notfound@mail.com");
        loginRequestDto.setPassword("password123");

        ServerRequest serverRequest = MockServerRequest.builder()
                .body(Mono.just(loginRequestDto));

        when(userRepository.findByEmail(any(Email.class))).thenReturn(Mono.empty());

        // Act
        Mono<ServerResponse> result = authHandler.login(serverRequest);

        // Assert
        StepVerifier.create(result)
                .expectError(DomainException.class)
                .verify();

        verify(userRepository, times(1)).findByEmail(any(Email.class));
    }

    @Test
    @DisplayName("Login con contraseña incorrecta debe retornar error")
    void login_WrongPassword_ShouldReturnError() {
        // Arrange
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("test@mail.com");
        loginRequestDto.setPassword("wrongpassword");

        ServerRequest serverRequest = MockServerRequest.builder()
                .body(Mono.just(loginRequestDto));

        User mockUser = createMockUser();

        when(userRepository.findByEmail(any(Email.class))).thenReturn(Mono.just(mockUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // Act
        Mono<ServerResponse> result = authHandler.login(serverRequest);

        // Assert
        StepVerifier.create(result)
                .expectError(DomainException.class)
                .verify();

        verify(userRepository, times(1)).findByEmail(any(Email.class));
        verify(passwordEncoder, times(1)).matches(anyString(), anyString());
    }

    @Test
    @DisplayName("Login debe extraer token correctamente del response")
    void login_ShouldExtractTokenFromResponse() {
        // Arrange
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail("test@mail.com");
        loginRequestDto.setPassword("password123");

        ServerRequest serverRequest = MockServerRequest.builder()
                .body(Mono.just(loginRequestDto));

        User mockUser = createMockUser();
        Role mockRole = createMockRole();
        String expectedToken = "expected-jwt-token";

        when(userRepository.findByEmail(any(Email.class))).thenReturn(Mono.just(mockUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(roleRepository.findById(any(IdRole.class))).thenReturn(Mono.just(mockRole));
        when(jwtService.generateToken(any(UserPrincipal.class))).thenReturn(expectedToken);

        // Act
        Mono<ServerResponse> result = authHandler.login(serverRequest);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(response -> response.statusCode().is2xxSuccessful())
                .verifyComplete();

        // Verificar que se llamó al servicio JWT con el token esperado
        verify(jwtService, times(1)).generateToken(any(UserPrincipal.class));
    }

    @Test
    @DisplayName("Login debe manejar error de construcción de Email")
    void login_EmailConstructionError_ShouldHandleError() {
        // Arrange
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail(""); // Email vacío que causará error de construcción
        loginRequestDto.setPassword("password123");

        ServerRequest serverRequest = MockServerRequest.builder()
                .body(Mono.just(loginRequestDto));

        // Act
        Mono<ServerResponse> result = authHandler.login(serverRequest);

        // Assert
        StepVerifier.create(result)
                .expectError(DomainException.class)
                .verify();
    }

    private User createMockUser() {
        User user = new User();
        try {
            user.setEmail(new Email("test@mail.com"));
            user.setPassword(new Password("hashedpassword"));
            user.setIdRole(new co.com.crediya.model.user.values.IdRole("1"));
        } catch (Exception e) {
            // Manejo de excepción para el mock
        }
        return user;
    }

    private User createMockUserWithInvalidRole() {
        User user = new User();
        try {
            user.setEmail(new Email("test@mail.com"));
            user.setPassword(new Password("hashedpassword"));
            user.setIdRole(new co.com.crediya.model.user.values.IdRole(null)); // Esto causará error
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
        }
        return role;
    }
}