package co.com.crediya.api;

import co.com.crediya.api.dto.LoginRequestDto;
import co.com.crediya.api.dto.LoginResponseDto;
import co.com.crediya.api.security.UserPrincipal;
import co.com.crediya.api.security.util.JwtService;
import co.com.crediya.api.util.CustomMapperWebFlux;
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
import reactor.util.function.Tuples;

@Component
public class AuthHandler {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthHandler(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public Mono<ServerResponse> login(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(LoginRequestDto.class)
                .flatMap(loginRequestDto ->
                        {
                            try {
                                return userRepository.findByEmail(new Email(loginRequestDto.getEmail()))
                                        .map(user -> Tuples.of(loginRequestDto, user));
                            } catch (ConstructionDomainException e) {
                                //TODO: mensaje exception
                                return Mono.error(new DomainException(""));
                            }
                        }
                )
                .filter(tuple ->
                        passwordEncoder.matches(tuple.getT1().getPassword(), tuple.getT2().getPassword().getPassword())
                )
                .flatMap(tuple -> {
                    try {
                        return roleRepository.findById(new IdRole(tuple.getT2().getIdRole().getIdRole().toString()))
                                .map(role -> {
                                    UserPrincipal userPrincipal = null;
                                    try {
                                        userPrincipal = CustomMapperWebFlux.userToUserPrincipal(tuple.getT2());
                                    } catch (ConstructionDomainException e) {
                                        // TODO::
                                        throw new RuntimeException(e);
                                    }
                                    userPrincipal.setNameRole(role.getNameRole().getNameRole());
                                    return userPrincipal;
                                });
                    } catch (ConstructionDomainException e) {
                        return Mono.error(new RuntimeException(e));
                    }
                }).flatMap(userPrincipal -> {
                    String generatedToken = jwtService.generateToken(userPrincipal);
                    return ServerResponse.ok().bodyValue(LoginResponseDto.builder()
                            .token(generatedToken)
                            .build());
                })
                .switchIfEmpty(Mono.error(new DomainException("Credenciales inválidas")));

    }
}
