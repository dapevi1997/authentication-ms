package co.com.crediya.api;

import co.com.crediya.api.dto.RegisterUserRequestDto;
import co.com.crediya.api.dto.RegisterUserResponseDto;
import co.com.crediya.api.util.ObjectMapper;
import co.com.crediya.requestvalidator.RequestValidator;
import co.com.crediya.usecase.getallroles.GetAllRolesUseCase;
import co.com.crediya.usecase.getuserbyemail.GetUserByEmailUseCase;
import co.com.crediya.usecase.registeruser.RegisterUserUseCase;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;


@Component
public class Handler {
    private final RequestValidator requestValidator;
    private  final RegisterUserUseCase registerUserUseCase;
    private final GetAllRolesUseCase getAllRolesUseCase;
    private final GetUserByEmailUseCase getUserByEmailUseCase;


    public Handler(RequestValidator requestValidator, RegisterUserUseCase registerUserUseCase, GetAllRolesUseCase getAllRolesUseCase, GetUserByEmailUseCase getUserByEmailUseCase) {
        this.requestValidator = requestValidator;
        this.registerUserUseCase = registerUserUseCase;
        this.getAllRolesUseCase = getAllRolesUseCase;
        this.getUserByEmailUseCase = getUserByEmailUseCase;
    }

    public Mono<ServerResponse> registerUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RegisterUserRequestDto.class)
                .flatMap(this::validateRequest)
                .flatMap( registerUserRequestDto ->
                                Mono.fromCallable(() -> ObjectMapper.registerUserRequestDtoToUser((RegisterUserRequestDto) registerUserRequestDto))
                        )
                .flatMap(registerUserUseCase::registerUser)
                .flatMap(user2 -> ServerResponse.ok().bodyValue("OK"));
    }

    private Mono<?> validateRequest(RegisterUserRequestDto registerUserRequestDto) {
        List<String> listErrors = requestValidator.validate(registerUserRequestDto);

        if (!listErrors.isEmpty()){
            return ServerResponse.badRequest()
                    .bodyValue(listErrors);
        }

        return Mono.just(registerUserRequestDto);
    }


}
