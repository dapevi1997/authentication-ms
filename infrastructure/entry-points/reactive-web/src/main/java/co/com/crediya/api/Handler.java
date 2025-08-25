package co.com.crediya.api;

import co.com.crediya.api.dto.RegisterUserRequestDto;
import co.com.crediya.api.dto.RegisterUserResponseDto;
import co.com.crediya.api.util.ObjectMapper;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.DomainException;
import co.com.crediya.requestvalidator.RequestValidator;
import co.com.crediya.usecase.getallroles.GetAllRolesUseCase;
import co.com.crediya.usecase.registeruser.RegisterUserUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;


@Component
@Slf4j
public class Handler {
    private final RequestValidator requestValidator;
    private  final RegisterUserUseCase registerUserUseCase;
    private final GetAllRolesUseCase getAllRolesUseCase;

    public Handler(RequestValidator requestValidator, RegisterUserUseCase registerUserUseCase, GetAllRolesUseCase getAllRolesUseCase) {
        this.requestValidator = requestValidator;
        this.registerUserUseCase = registerUserUseCase;
        this.getAllRolesUseCase = getAllRolesUseCase;
    }

    public Mono<ServerResponse> registerUser(ServerRequest serverRequest) {
        log.info("Iniciando proceso de registro de usuario");
        return serverRequest.bodyToMono(RegisterUserRequestDto.class)
                .doOnNext(registerUserRequestDto -> log.info("Request recibido {}", registerUserRequestDto))
                .flatMap(this::validateRequest)
                .flatMap(requestDto -> Mono.fromCallable( () -> ObjectMapper.registerUserRequestDtoToUser(requestDto)))
                .flatMap(this::validateRolExits)
                .flatMap(registerUserUseCase::registerUser)
                .flatMap(user -> {
                    RegisterUserResponseDto registerUserResponseDto = new RegisterUserResponseDto();
                    return ServerResponse.ok().bodyValue(registerUserResponseDto);
                })
                .onErrorResume(DomainException.class , ex -> {
                    RegisterUserResponseDto registerUserResponseDto = new RegisterUserResponseDto();
                    return ServerResponse.badRequest().bodyValue(registerUserResponseDto);
                })
                .onErrorResume(Exception.class, exception -> {
                    RegisterUserResponseDto registerUserResponseDto = new RegisterUserResponseDto();
                    return ServerResponse.status(500).bodyValue(registerUserResponseDto);
                });
    }

    private Mono<User> validateRolExits(User user) {
        return getAllRolesUseCase.getAllRoles()
                .filter(role -> role.getIdRole().equals(user.getIdRole()))
                .next()
                .map(role -> user)
                .switchIfEmpty(Mono.error(new DomainException("No se encuentra rol configurado en el sistema")));
    }

    private Mono<RegisterUserRequestDto> validateRequest(RegisterUserRequestDto registerUserRequestDto) {
        List<String> listErrors = requestValidator.validate(registerUserRequestDto);

        if (!listErrors.isEmpty()){
            log.error("Error en el request");
            return Mono.error(new DomainException(String.join(", ", listErrors)));
        }
        return Mono.just(registerUserRequestDto);
    }


}
