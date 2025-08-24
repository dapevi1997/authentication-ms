package co.com.crediya.api;

import co.com.crediya.api.dto.RegisterUserRequestDto;
import co.com.crediya.api.dto.RegisterUserResponseDto;
import co.com.crediya.requestvalidator.RequestValidator;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class Handler {
    private final RequestValidator requestValidator;
//private  final UseCase useCase;


    public Handler(RequestValidator requestValidator) {
        this.requestValidator = requestValidator;
    }

    public Mono<ServerResponse> registerUser(ServerRequest serverRequest) {
        // useCase.logic();
        return serverRequest.bodyToMono(RegisterUserRequestDto.class)
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
                });
    }
}
