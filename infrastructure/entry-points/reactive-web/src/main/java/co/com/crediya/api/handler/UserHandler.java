package co.com.crediya.api.handler;

import co.com.crediya.api.dto.RegisterUserRequestDto;
import co.com.crediya.api.dto.RegisterUserResponseDto;
import co.com.crediya.api.exception.BadRequestException;
import co.com.crediya.api.util.CustomMapperWebFlux;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.DomainException;
import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.requestvalidator.RequestValidator;
import co.com.crediya.usecase.getallroles.GetAllRolesUseCase;
import co.com.crediya.usecase.registeruser.RegisterUserUseCase;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class UserHandler {
    private final RequestValidator requestValidator;
    private  final RegisterUserUseCase registerUserUseCase;
    private final GetAllRolesUseCase getAllRolesUseCase;
    private final ObjectMapper objectMapper;
    private final CustomMapperWebFlux customMaper;

    public UserHandler(RequestValidator requestValidator, RegisterUserUseCase registerUserUseCase, GetAllRolesUseCase getAllRolesUseCase, ObjectMapper objectMapper, CustomMapperWebFlux customMaper) {
        this.requestValidator = requestValidator;
        this.registerUserUseCase = registerUserUseCase;
        this.getAllRolesUseCase = getAllRolesUseCase;
        this.objectMapper = objectMapper;
        this.customMaper = customMaper;
    }

    public Mono<ServerResponse> registerUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RegisterUserRequestDto.class)
                .switchIfEmpty(Mono.error(new BadRequestException("El body de la peticion no puede ser vacío")))
                .doOnNext(registerUserRequestDto -> log.info("Agregar usuario request recibido {}", objectMapper.map(registerUserRequestDto, RegisterUserRequestDto.class)))
                .flatMap(this::validateRequest)
                .flatMap(this::buildandReturnUserDomail)
                .flatMap(this::validateRolExits)
                .flatMap(registerUserUseCase::registerUser)
                .flatMap(user -> {
                    RegisterUserResponseDto registerUserResponseDto = new RegisterUserResponseDto();
                    registerUserResponseDto.setIdUser(user.getIdUser().toString());
                    registerUserResponseDto.setIdRole(user.getIdRole().toString());
                    registerUserResponseDto.setMessage("Usuario registrado exitosamente.");
                    return ServerResponse.ok().bodyValue(registerUserResponseDto);
                });
    }

    private Mono<User> buildandReturnUserDomail(RegisterUserRequestDto registerUserRequestDto) {
        return Mono.fromCallable(() -> customMaper.registerUserRequestDtoToUser(registerUserRequestDto))
                .onErrorResume(ConstructionDomainException.class, ex -> {
            log.error("Error al construir al tratar de contruir Usuario del dominio: {}", ex.getMessage());
            return Mono.error(new ConstructionDomainException("Error al construir el Usuario del dominio: " + ex.getMessage()));
        });
    }


    private Mono<User> validateRolExits(User user) {
        return getAllRolesUseCase.getAllRoles()
                .filter(role -> role.getIdRole().getIdRole().equals(user.getIdRole().getIdRole()))
                .next()
                .map(role -> user)
                .switchIfEmpty(Mono.defer(() -> {
                    log.error("No se encuentra rol configurado en el sistema con el id: {}", user.getIdRole().getIdRole());
                    return Mono.error(new DomainException("No se encuentra rol configurado en el sistema"));
                }));
    }

    private Mono<RegisterUserRequestDto> validateRequest(RegisterUserRequestDto registerUserRequestDto) {
        List<String> listErrors = requestValidator.validate(registerUserRequestDto);

        if (!listErrors.isEmpty()) {
            log.error("Error en el request de agregar usuario con email {}. Errores: {}", registerUserRequestDto.getEmail(), String.join(", ", listErrors));
            return Mono.error(new BadRequestException(String.join(", ", listErrors)));
        }
        return Mono.just(registerUserRequestDto);
    }


}
