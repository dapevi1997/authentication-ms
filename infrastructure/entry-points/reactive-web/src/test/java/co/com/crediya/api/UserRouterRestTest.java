package co.com.crediya.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDate;
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
import co.com.crediya.api.config.UserPath;
import co.com.crediya.api.dto.FindUserResponseDto;
import co.com.crediya.api.dto.RegisterUserRequestDto;
import co.com.crediya.api.dto.RegisterUserResponseDto;
import co.com.crediya.api.handler.UserHandler;
import co.com.crediya.api.route.UserRouterRest;
import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.exception.DomainException;
import reactor.core.publisher.Mono;

@SpringJUnitConfig
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {UserRouterRestTest.TestConfig.class})
class UserRouterRestTest {

    private WebTestClient webTestClient;

    @Mock
    private UserHandler userHandler;

    @Configuration
    static class TestConfig {
        @Bean
        public UserPath userPath() {
            UserPath path = new UserPath();
            path.setSaveUser("/api/v1/usuarios");
            path.setFindUserByEmail("/api/v1/usuarios");
            return path;
        }

        @Bean
        public UserRouterRest userRouterRest(UserPath userPath) {
            return new UserRouterRest(userPath);
        }
    }

    @BeforeEach
    void setUp() {
        UserPath userPath = new UserPath();
        userPath.setSaveUser("/api/v1/usuarios");
        userPath.setFindUserByEmail("/api/v1/usuarios");

        UserRouterRest userRouterRest = new UserRouterRest(userPath);
        RouterFunction<ServerResponse> routerFunction =
                userRouterRest.userRouterFunction(userHandler);

        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @Test
    @DisplayName("Registro de usuario exitoso debe retornar usuario registrado")
    void registerUser_Success_ShouldReturnRegisteredUser() {
        // Arrange
        RegisterUserRequestDto registerRequest = RegisterUserRequestDto.builder().name("Juan")
                .lastName("Pérez").email("juan.perez@mail.com").password("password123")
                .address("Calle 123 #45-67").documentId("12345678").phone("3001234567")
                .userBirthday("1990-01-01").baseSalary("2500000").idRole("1").build();

        RegisterUserResponseDto expectedResponse = RegisterUserResponseDto.builder().idUser("1")
                .idRole("1").email("juan.perez@mail.com").nombre("Juan").salarioBase("2500000")
                .message("Usuario registrado exitosamente").build();

        when(userHandler.registerUser(any(ServerRequest.class)))
                .thenReturn(ServerResponse.ok().bodyValue(expectedResponse));

        // Act & Assert
        webTestClient.post().uri("/api/v1/usuarios").contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(registerRequest)).exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(RegisterUserResponseDto.class);
    }

    @Test
    @DisplayName("Registro con email duplicado debe retornar error")
    void registerUser_DuplicateEmail_ShouldReturnError() {
        // Arrange
        RegisterUserRequestDto registerRequest = RegisterUserRequestDto.builder().name("Juan")
                .lastName("Pérez").email("existing@mail.com").password("password123")
                .address("Calle 123 #45-67").documentId("12345678").phone("3001234567")
                .userBirthday("1990-01-01").baseSalary("2500000").idRole("1").build();

        when(userHandler.registerUser(any(ServerRequest.class))).thenReturn(Mono
                .error(new DomainException("Ya existe un usuario con email existing@mail.com")));

        // Act & Assert
        webTestClient.post().uri("/api/v1/usuarios").contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(registerRequest)).exchange().expectStatus()
                .is5xxServerError();
    }

    @Test
    @DisplayName("Registro con datos inválidos debe retornar error")
    void registerUser_InvalidData_ShouldReturnError() {
        // Arrange
        RegisterUserRequestDto registerRequest = RegisterUserRequestDto.builder().name("") // Nombre
                                                                                           // vacío
                .lastName("Pérez").email("invalid-email") // Email inválido
                .password("password123").address("Calle 123 #45-67").documentId("12345678")
                .phone("3001234567").userBirthday("1990-01-01").baseSalary("2500000").idRole("1")
                .build();

        when(userHandler.registerUser(any(ServerRequest.class))).thenReturn(Mono.error(
                new ConstructionDomainException("Error al construir el Usuario del dominio")));

        // Act & Assert
        webTestClient.post().uri("/api/v1/usuarios").contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(registerRequest)).exchange().expectStatus()
                .is5xxServerError();
    }

    @Test
    @DisplayName("Registro con rol inexistente debe retornar error")
    void registerUser_NonExistentRole_ShouldReturnError() {
        // Arrange
        RegisterUserRequestDto registerRequest = RegisterUserRequestDto.builder().name("Juan")
                .lastName("Pérez").email("juan.perez@mail.com").password("password123")
                .address("Calle 123 #45-67").documentId("12345678").phone("3001234567")
                .userBirthday("1990-01-01").baseSalary("2500000").idRole("999") // Rol inexistente
                .build();

        when(userHandler.registerUser(any(ServerRequest.class))).thenReturn(
                Mono.error(new DomainException("No se encuentra rol configurado en el sistema")));

        // Act & Assert
        webTestClient.post().uri("/api/v1/usuarios").contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(registerRequest)).exchange().expectStatus()
                .is5xxServerError();
    }

    @Test
    @DisplayName("Buscar usuario por email exitoso debe retornar usuario")
    void findUserByEmail_Success_ShouldReturnUser() {
        // Arrange
        String email = "test@mail.com";
        FindUserResponseDto expectedResponse = FindUserResponseDto.builder()
                .idUser(1L).name("Juan").lastName("Pérez").email(email).documentId("12345678")
                .phone("3001234567").address("Calle 123 #45-67").birthday(LocalDate.of(1990, 1, 1))
                .baseSalary(new BigDecimal("2500000")).idRole(1L).build();

        when(userHandler.findUserByEmail(any(ServerRequest.class)))
                .thenReturn(ServerResponse.ok().bodyValue(expectedResponse));

        // Act & Assert
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/usuarios").queryParam("email", email)
                        .build())
                .exchange().expectStatus().isOk().expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody(FindUserResponseDto.class);
    }

    @Test
    @DisplayName("Buscar usuario sin email debe retornar error")
    void findUserByEmail_MissingEmail_ShouldReturnError() {
        // Arrange
        when(userHandler.findUserByEmail(any(ServerRequest.class)))
                .thenReturn(Mono.error(new DomainException("El parámetro 'email' es obligatorio")));

        // Act & Assert
        webTestClient.get().uri("/api/v1/usuarios").exchange().expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("Buscar usuario con email inexistente debe retornar error")
    void findUserByEmail_NonExistentEmail_ShouldReturnError() {
        // Arrange
        String email = "nonexistent@mail.com";

        when(userHandler.findUserByEmail(any(ServerRequest.class)))
                .thenReturn(Mono.error(new DomainException("Usuario no encontrado")));

        // Act & Assert
        webTestClient
                .get().uri(uriBuilder -> uriBuilder.path("/api/v1/usuarios")
                        .queryParam("email", email).build())
                .exchange().expectStatus().is5xxServerError();
    }

    @Test
    @DisplayName("Método PUT no permitido debe retornar error 404")
    void putMethod_ShouldReturnNotFound() {
        // Act & Assert
        webTestClient.put().uri("/api/v1/usuarios").contentType(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isNotFound(); // RouterFunction no maneja PUT
    }

    @Test
    @DisplayName("Método DELETE no permitido debe retornar error 404")
    void deleteMethod_ShouldReturnNotFound() {
        // Act & Assert
        webTestClient.delete().uri("/api/v1/usuarios").exchange().expectStatus().isNotFound(); // RouterFunction
                                                                                               // no
                                                                                               // maneja
                                                                                               // DELETE
    }

    @Test
    @DisplayName("Registro con excepción interna debe manejar error correctamente")
    void registerUser_InternalException_ShouldHandleError() {
        // Arrange
        RegisterUserRequestDto registerRequest = RegisterUserRequestDto.builder().name("Juan")
                .lastName("Pérez").email("juan.perez@mail.com").password("password123")
                .address("Calle 123 #45-67").documentId("12345678").phone("3001234567")
                .userBirthday("1990-01-01").baseSalary("2500000").idRole("1").build();

        when(userHandler.registerUser(any(ServerRequest.class)))
                .thenReturn(Mono.error(new RuntimeException("Error interno del servidor")));

        // Act & Assert
        webTestClient.post().uri("/api/v1/usuarios").contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(registerRequest)).exchange().expectStatus()
                .is5xxServerError();
    }

    @Test
    @DisplayName("Buscar usuario con excepción interna debe manejar error correctamente")
    void findUserByEmail_InternalException_ShouldHandleError() {
        // Arrange
        String email = "test@mail.com";

        when(userHandler.findUserByEmail(any(ServerRequest.class)))
                .thenReturn(Mono.error(new RuntimeException("Error interno del servidor")));

        // Act & Assert
        webTestClient
                .get().uri(uriBuilder -> uriBuilder.path("/api/v1/usuarios")
                        .queryParam("email", email).build())
                .exchange().expectStatus().is5xxServerError();
    }
}
