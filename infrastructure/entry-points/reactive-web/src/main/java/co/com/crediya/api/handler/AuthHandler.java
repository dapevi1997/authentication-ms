package co.com.crediya.api.handler;

import co.com.crediya.api.dto.LoginRequestDto;
import co.com.crediya.api.dto.LoginResponseDto;
import co.com.crediya.api.security.UserPrincipal;
import co.com.crediya.api.security.util.JwtService;
import co.com.crediya.api.util.Constantes;
import co.com.crediya.api.util.CustomMapperWebFlux;
import co.com.crediya.model.logger.LoggerGateway;
import co.com.crediya.model.role.gateways.RoleRepository;
import co.com.crediya.model.role.values.IdRole;
import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.exception.DomainException;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.model.user.values.Email;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class AuthHandler {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final LoggerGateway loggerGateway;

    public AuthHandler(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtService jwtService, LoggerGateway loggerGateway) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.loggerGateway = loggerGateway;
    }

    public Mono<ServerResponse> login(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(LoginRequestDto.class)
                .flatMap(this::validateUserCredentials)
                .flatMap(this::buildUserPrincipal)
                .flatMap(this::generateTokenResponse);
    }

    private Mono<UserWithRequest> validateUserCredentials(LoginRequestDto loginRequestDto) {
        return Mono.fromCallable(() -> new Email(loginRequestDto.getEmail()))
                .onErrorMap(ConstructionDomainException.class, e -> {
                    loggerGateway.error("{} Error validando email: {}", Constantes.TRAZA_AUTH, e.getMessage());
                    return new DomainException("Email inválido");
                })
                .flatMap(email -> {
                    loggerGateway.info("{} Se va a buscar usuario por email {}", Constantes.TRAZA_AUTH, loginRequestDto.getEmail());

                    return userRepository.findByEmail(email)
                            .switchIfEmpty(Mono.defer(() -> {
                                loggerGateway.error("{} Usuario no encontrado con email {}", Constantes.TRAZA_AUTH, loginRequestDto.getEmail());
                                return Mono.error(new DomainException("Credenciales inválidas"));
                            }))
                            .filter(user -> passwordEncoder.matches(
                                    loginRequestDto.getPassword(),
                                    user.getPassword().getPassword()
                            ))
                            .switchIfEmpty(Mono.defer(() -> {
                                loggerGateway.error(Constantes.TRAZA_AUTH + "Revisar credenciales. Usuario ingresado: {}", loginRequestDto.getEmail());
                                return Mono.error(new DomainException("Credenciales inválidas"));
                            }))
                            .map(user -> new UserWithRequest(loginRequestDto, user));
                });
    }


    private Mono<UserPrincipal> buildUserPrincipal(UserWithRequest userWithRequest) {
        return Mono.fromCallable(() ->
                        new IdRole(userWithRequest.user().getIdRole().getIdRole().toString())
                )
                .onErrorMap(ConstructionDomainException.class, e -> {
                    loggerGateway.error(Constantes.TRAZA_AUTH + "Error constructor IdRole, mensaje: {}", e.getMessage());
                    return new DomainException("Error al construir IdRole");
                })
                .flatMap(idRole ->
                        roleRepository.findById(idRole)
                                .map(role -> {
                                    UserPrincipal userPrincipal = CustomMapperWebFlux.userToUserPrincipal(userWithRequest.user());
                                    userPrincipal.setNameRole(role.getNameRole().getNameRole());
                                    return userPrincipal;
                                })
                );
    }

    private Mono<ServerResponse> generateTokenResponse(UserPrincipal userPrincipal) {
        return Mono.fromSupplier(() -> jwtService.generateToken(userPrincipal))
                .doOnNext(token ->
                        loggerGateway.info(Constantes.TRAZA_AUTH + "Token generado para usuario {}", userPrincipal.getEmail())
                )
                .map(token -> LoginResponseDto.builder()
                        .token(token)
                        .build()
                )
                .flatMap(responseDto -> ServerResponse.ok().bodyValue(responseDto));
    }

    private record UserWithRequest(LoginRequestDto request, co.com.crediya.model.user.User user) {}
}
