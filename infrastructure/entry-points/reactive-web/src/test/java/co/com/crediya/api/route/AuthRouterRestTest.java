package co.com.crediya.api.route;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import co.com.crediya.api.config.AuthPath;
import co.com.crediya.api.dto.LoginRequestDto;
import co.com.crediya.api.dto.LoginResponseDto;
import co.com.crediya.api.handler.AuthHandler;
import co.com.crediya.model.user.exception.DomainException;
import reactor.core.publisher.Mono;

@SpringJUnitConfig
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {AuthRouterRestTest.TestConfig.class})
class AuthRouterRestTest {

    private WebTestClient webTestClient;

    @Mock
    private AuthHandler authHandler;

    @Configuration
    static class TestConfig {
        @Bean
        public AuthPath authPath() {
            AuthPath path = new AuthPath();
            path.setLogin("/api/v1/login");
            return path;
        }

        @Bean
        public AuthRouterRest authRouterRest(AuthPath authPath) {
            return new AuthRouterRest(authPath);
        }
    }

    @BeforeEach
    void setUp() {
        AuthPath authPath = new AuthPath();
        authPath.setLogin("/api/v1/login");

        AuthRouterRest authRouterRest = new AuthRouterRest(authPath);
        RouterFunction<ServerResponse> routerFunction =
                authRouterRest.authRouterFunction(authHandler);

        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @Test
    @DisplayName("Login exitoso debe retornar token")
    void login_Success_ShouldReturnToken() {
        // Arrange
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setEmail("admin@mail.com");
        loginRequest.setPassword("password123");

        LoginResponseDto expectedResponse =
                LoginResponseDto.builder().token("mock-jwt-token").build();

        when(authHandler.login(any(ServerRequest.class)))
                .thenReturn(ServerResponse.ok().bodyValue(expectedResponse));

        // Act & Assert
        webTestClient.post().uri("/api/v1/login").contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(loginRequest)).exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(LoginResponseDto.class).isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("Login con credenciales inválidas debe retornar error")
    void login_InvalidCredentials_ShouldReturnError() {
        // Arrange
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setEmail("invalid@mail.com");
        loginRequest.setPassword("wrongpassword");

        when(authHandler.login(any(ServerRequest.class)))
                .thenReturn(Mono.error(new DomainException("Credenciales inválidas")));

        // Act & Assert
        webTestClient.post().uri("/api/v1/login").contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(loginRequest)).exchange().expectStatus()
                .is5xxServerError();
    }

    @Test
    @DisplayName("Login con email vacío debe fallar en validación")
    void login_EmptyEmail_ShouldReturnError() {
        // Arrange
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setEmail("");
        loginRequest.setPassword("password123");

        when(authHandler.login(any(ServerRequest.class)))
                .thenReturn(Mono.error(new DomainException("Email inválido")));

        // Act & Assert
        webTestClient.post().uri("/api/v1/login").contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(loginRequest)).exchange().expectStatus()
                .is5xxServerError();
    }

    @Test
    @DisplayName("Login con password vacío debe fallar en validación")
    void login_EmptyPassword_ShouldReturnError() {
        // Arrange
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setEmail("admin@mail.com");
        loginRequest.setPassword("");

        when(authHandler.login(any(ServerRequest.class)))
                .thenReturn(Mono.error(new DomainException("Credenciales inválidas")));

        // Act & Assert
        webTestClient.post().uri("/api/v1/login").contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(loginRequest)).exchange().expectStatus()
                .is5xxServerError();
    }

    @Test
    @DisplayName("Acceso a ruta no existente debe retornar 404")
    void accessNonExistentRoute_ShouldReturn404() {
        // Act & Assert
        webTestClient.post().uri("/api/v1/nonexistent").contentType(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isNotFound();
    }

    @Test
    @DisplayName("Login con metodo GET debe retornar error de metodo no permitido")
    void login_GetMethod_ShouldReturnMethodNotAllowed() {
        // Act & Assert
        webTestClient.get().uri("/api/v1/login").exchange().expectStatus().isNotFound(); // RouterFunction
                                                                                         // no
                                                                                         // maneja
                                                                                         // GET,
                                                                                         // devuelve
                                                                                         // 404
    }

    @Test
    @DisplayName("Login con excepción interna debe manejar error correctamente")
    void login_InternalException_ShouldHandleError() {
        // Arrange
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setEmail("admin@mail.com");
        loginRequest.setPassword("password123");

        when(authHandler.login(any(ServerRequest.class)))
                .thenReturn(Mono.error(new RuntimeException("Error interno del servidor")));

        // Act & Assert
        webTestClient.post().uri("/api/v1/login").contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(loginRequest)).exchange().expectStatus()
                .is5xxServerError();
    }

    @Test
    @DisplayName("Login con handler que retorna null debe manejar error")
    void login_HandlerReturnsNull_ShouldHandleError() {
        // Arrange
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setEmail("admin@mail.com");
        loginRequest.setPassword("password123");

        when(authHandler.login(any(ServerRequest.class))).thenReturn(null);

        // Act & Assert
        webTestClient.post().uri("/api/v1/login").contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(loginRequest)).exchange().expectStatus()
                .is5xxServerError();
    }

    @Test
    @DisplayName("Login exitoso con diferentes códigos de estado")
    void login_SuccessWithDifferentStatus() {
        // Arrange
        LoginRequestDto loginRequest = new LoginRequestDto();
        loginRequest.setEmail("admin@mail.com");
        loginRequest.setPassword("password123");

        LoginResponseDto expectedResponse =
                LoginResponseDto.builder().token("mock-jwt-token").build();

        when(authHandler.login(any(ServerRequest.class)))
                .thenReturn(ServerResponse.status(201).bodyValue(expectedResponse));

        // Act & Assert
        webTestClient.post().uri("/api/v1/login").contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(loginRequest)).exchange().expectStatus().isCreated()
                .expectBody(LoginResponseDto.class).isEqualTo(expectedResponse);
    }
}
