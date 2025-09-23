package co.com.crediya.api.handler;

import co.com.crediya.api.dto.RegisterUserRequestDto;
import co.com.crediya.api.dto.RegisterUserResponseDto;
import co.com.crediya.api.exception.BadRequestException;
import co.com.crediya.api.util.Constantes;
import co.com.crediya.api.util.CustomMapperWebFlux;
import co.com.crediya.model.logger.LoggerGateway;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.DomainException;
import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.gateways.UserRepository;
import co.com.crediya.requestvalidator.RequestValidator;
import co.com.crediya.usecase.finduserbyemail.FindUserByEmailUseCase;
import co.com.crediya.usecase.getallroles.GetAllRolesUseCase;
import co.com.crediya.usecase.registeruser.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UserHandler {
    private final RequestValidator requestValidator;
    private  final RegisterUserUseCase registerUserUseCase;
    private final GetAllRolesUseCase getAllRolesUseCase;
    private final ObjectMapper objectMapper;
    private final CustomMapperWebFlux customMaper;
    private final LoggerGateway loggerGateway;
    private final FindUserByEmailUseCase findUserByEmailUseCase;
    private final UserRepository userRepository;

    public Mono<ServerResponse> registerUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RegisterUserRequestDto.class)
                .switchIfEmpty(Mono.error(new BadRequestException("El body de la peticion no puede ser vacío")))
                .doOnNext(registerUserRequestDto -> loggerGateway.info("Agregar usuario request recibido {}", objectMapper.map(registerUserRequestDto, RegisterUserRequestDto.class)))
                .flatMap(this::validateRequest)
                .flatMap(this::buildandReturnUserDomail)
                .flatMap(this::validateRolExits)
                .flatMap(registerUserUseCase::registerUser)
                .flatMap(user -> ServerResponse.ok().bodyValue(
                        RegisterUserResponseDto.builder()
                                .idUser(user.getIdUser().toString())
                                .idRole(user.getIdRole().toString())
                                .email(user.getEmail().getEmailUser())
                                .nombre(user.getName().getNameUser())
                                .salarioBase(user.getBaseSalary().getBaseSalaryUser().toString())
                                .message("Usuario registrado exitosamente")
                                .build()
                ));
    }

    private Mono<User> buildandReturnUserDomail(RegisterUserRequestDto registerUserRequestDto) {
        return Mono.fromCallable(() -> customMaper.registerUserRequestDtoToUser(registerUserRequestDto))
                .onErrorResume(ConstructionDomainException.class, ex -> {
            loggerGateway.error("Error al construir al tratar de contruir Usuario del dominio: {}", ex.getMessage());
            return Mono.error(new ConstructionDomainException("Error al construir el Usuario del dominio: " + ex.getMessage()));
        });
    }


    private Mono<User> validateRolExits(User user) {
        return getAllRolesUseCase.getAllRoles()
                .filter(role -> role.getIdRole().getIdRole().equals(user.getIdRole().getIdRole()))
                .next()
                .map(role -> user)
                .switchIfEmpty(Mono.defer(() -> {
                    loggerGateway.error("No se encuentra rol configurado en el sistema con el id: {}", user.getIdRole().getIdRole());
                    return Mono.error(new DomainException("No se encuentra rol configurado en el sistema"));
                }));
    }

    private Mono<RegisterUserRequestDto> validateRequest(RegisterUserRequestDto registerUserRequestDto) {
        return Mono.defer(() -> {
            List<String> listErrors = requestValidator.validate(registerUserRequestDto);

            if (!listErrors.isEmpty()) {
                String errors = String.join(", ", listErrors);
                loggerGateway.error(
                        "Error en el request de agregar usuario con email {}. Errores: {}",
                        registerUserRequestDto.getEmail(),
                        errors
                );
                return Mono.error(new BadRequestException(errors));
            }

            return Mono.just(registerUserRequestDto);
        });
    }

    public Mono<ServerResponse> findUserByEmail(ServerRequest serverRequest) {
        return Mono.justOrEmpty(serverRequest.queryParam("email"))
                .switchIfEmpty(Mono.error(new BadRequestException("El parámetro 'email' es obligatorio")))
                .flatMap(findUserByEmailUseCase::findUserByEmail)
                .map(customMaper::userToFindUSerByEmailDto)
                .flatMap(dto -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(dto)
                );
    }

    public Mono<ServerResponse> findUserByRoleName(ServerRequest serverRequest) {
        return Mono.justOrEmpty(serverRequest.queryParam(Constantes.QueryParams.ROL))
                .switchIfEmpty(Mono.error(new BadRequestException(Constantes.MensajesExcepciones.PARAMETRO_ROL_OBLIGATORIO)))
                .flatMapMany(userRepository::findAllUserByRoleName)
                .map(customMaper::userToFindUSerByEmailDto)
                .collectList()
                .doOnNext(list -> loggerGateway.info(Constantes.MensajesLogger.NUMERO_USUARIOS_POR_ROL,
                        serverRequest.queryParam(Constantes.QueryParams.ROL), list.size()))
                .flatMap(list -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(list)
                );
    }
}
