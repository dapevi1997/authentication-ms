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
        try {
            loggerGateway.info("{} Se va a buscar usuario por email {}", Constantes.TRAZA_AUTH, loginRequestDto.getEmail());

            return userRepository.findByEmail(new Email(loginRequestDto.getEmail()))
                    .switchIfEmpty(
                            Mono.defer(() -> {
                                loggerGateway.error("{} Usuario no encontrado con email {}", Constantes.TRAZA_AUTH, loginRequestDto.getEmail());
                                return Mono.error(new DomainException("Credenciales inválidas"));
                            })
                    )
                    .filter(user -> passwordEncoder.matches(
                            loginRequestDto.getPassword(),
                            user.getPassword().getPassword()
                    ))
                    .switchIfEmpty(
                            Mono.defer(() -> {
                                loggerGateway.error(Constantes.TRAZA_AUTH + "Revisar credenciales. Usuario ingresado: {}",  loginRequestDto.getEmail());
                                return Mono.error(new DomainException("Credenciales inválidas"));
                            })
                    )
                    .map(user -> new UserWithRequest(loginRequestDto, user));

        } catch (ConstructionDomainException e) {
            loggerGateway.error("{} Error validando email: {}", Constantes.TRAZA_AUTH, e.getMessage());
            return Mono.error(new DomainException("Email inválido"));
        }
    }


    private Mono<UserPrincipal> buildUserPrincipal(UserWithRequest userWithRequest) {
        IdRole idRole;
        try {
            idRole = new IdRole(userWithRequest.user().getIdRole().getIdRole().toString());
        } catch (ConstructionDomainException e) {
            loggerGateway.error(Constantes.TRAZA_AUTH  + "Error contructor IdRole, mensaje: {}", e.getMessage());
            return Mono.error(new DomainException("Error al construir IdRole"));
        }

        return roleRepository.findById(idRole)
                .map(role -> {
                    UserPrincipal userPrincipal = CustomMapperWebFlux.userToUserPrincipal(userWithRequest.user());
                    userPrincipal.setNameRole(role.getNameRole().getNameRole());
                    return userPrincipal;
                });
    }

    private Mono<ServerResponse> generateTokenResponse(UserPrincipal userPrincipal) {
        String generatedToken = jwtService.generateToken(userPrincipal);
        loggerGateway.info(Constantes.TRAZA_AUTH + "Token generado para usuario {}", userPrincipal.getEmail());
        return ServerResponse.ok().bodyValue(
                LoginResponseDto.builder()
                        .token(generatedToken)
                        .build()
        );
    }

    private record UserWithRequest(LoginRequestDto request, co.com.crediya.model.user.User user) {}
}
