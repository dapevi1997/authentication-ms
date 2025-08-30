package co.com.crediya.api.openapiutil;

import co.com.crediya.api.dto.ErrorResponseDto;
import co.com.crediya.api.dto.RegisterUserRequestDto;
import co.com.crediya.api.dto.RegisterUserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class OpenAPIDocs{
 @Operation(
                    operationId = "registerUser",
                    summary = "Registrar un nuevo usuario",
                    requestBody = @RequestBody(
                            useParameterTypeSchema = true,
                            description = "Json asociado a la request",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = RegisterUserRequestDto.class)
                            )
                    ),
                    responses = {
                            @ApiResponse(
                                    responseCode = "200", description = "Usuario registrado correctamente",
                                    content = @Content(schema = @Schema(implementation = RegisterUserResponseDto.class))
                            ),
                            @ApiResponse(
                                    responseCode = "400", description = "Error en la solicitud",
                                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                            ),
                            @ApiResponse(
                                    responseCode = "409", description = "Conflicto al registrar el usuario",
                                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                            ),
                            @ApiResponse(
                                    responseCode = "500", description = "Error interno del servidor",
                                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))
                            )
                    }
            )
    public Mono<ServerResponse> registerUserOperation(ServerRequest request) {
        return null;
    }
}