package co.com.crediya.api;

import co.com.crediya.api.dto.RegisterUserRequestDto;
import co.com.crediya.api.dto.RegisterUserResponseDto;
import co.com.crediya.api.util.ObjectMapper;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.UserContructionException;
import co.com.crediya.model.user.values.*;
import co.com.crediya.requestvalidator.RequestValidator;
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


    public Handler(RequestValidator requestValidator, RegisterUserUseCase registerUserUseCase) {
        this.requestValidator = requestValidator;
        this.registerUserUseCase = registerUserUseCase;
    }

    public Mono<ServerResponse> registerUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(RegisterUserRequestDto.class)
                .flatMap( registerUserRequestDto -> Mono.fromCallable(() -> ObjectMapper.registerUserRequestDtoToUser(registerUserRequestDto))
                        )
                .flatMap(user -> {
                    return registerUserUseCase.registerUser(user);
                })
                .flatMap(user -> ServerResponse.ok().bodyValue("OK"));
        // useCase.logic();
/*        return serverRequest.bodyToMono(RegisterUserRequestDto.class)
                .flatMap(registerUserRequestDto -> {
                    // Validar request recibida
                    List<String> listErrors = requestValidator.validate(registerUserRequestDto);

                    if (!listErrors.isEmpty()){
                        return ServerResponse.badRequest()
                                .bodyValue(listErrors);
                    }
                    RegisterUserResponseDto registerUserResponseDto = new RegisterUserResponseDto();
                    registerUserResponseDto.setMessage("OK");

                    return ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                            .bodyValue(registerUserResponseDto);
                });*/
    }
}
