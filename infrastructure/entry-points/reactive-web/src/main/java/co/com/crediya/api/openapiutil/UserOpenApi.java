package co.com.crediya.api.openapiutil;

import co.com.crediya.api.dto.*;
import lombok.experimental.UtilityClass;
import org.springdoc.core.fn.builders.operation.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.content.Builder.contentBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.core.fn.builders.schema.Builder.schemaBuilder;

@UtilityClass
public class UserOpenApi {
    private final String CONFLICT_CODE = String.valueOf(HttpStatus.CONFLICT.value());
    private final String CREATED_CODE = String.valueOf(HttpStatus.CREATED.value());
    private final String BAD_REQUEST_CODE = String.valueOf(HttpStatus.BAD_REQUEST.value());
    private final String INTERNAL_ERROR_CODE = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());
    private final String UNAUTHORIZED_CODE = String.valueOf(HttpStatus.UNAUTHORIZED.value());
    private final String UNAUTHORIZED = HttpStatus.UNAUTHORIZED.getReasonPhrase();
    private final String FORBIDDEN_CODE = String.valueOf(HttpStatus.FORBIDDEN.value());
    private final String FORBIDDEN = HttpStatus.FORBIDDEN.getReasonPhrase();

    public Builder registerUser(Builder builder) {
        return builder
                .operationId("registerUser")
                .summary("Registrar un nuevo usuario")
                .description("Endpoint para registrar un nuevo usuario")
                .tag("Users")
                .security(org.springdoc.core.fn.builders.securityrequirement.Builder.securityRequirementBuilder().name("bearerAuth"))

                // requestBody
                .requestBody(requestBodyBuilder()
                        .required(true)
                        .description("Json asociado a la request")
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(RegisterUserRequestDto.class))))

                // 200 OK
                .response(responseBuilder()
                        .responseCode(CREATED_CODE)
                        .description("Usuario registrado correctamente")
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(RegisterUserResponseDto.class))))

                // 400 Bad Request
                .response(responseBuilder()
                        .responseCode(BAD_REQUEST_CODE)
                        .description("Error en la solicitud")
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponseDto.class))))

                // 401 Unauthorized
                .response(responseBuilder()
                        .responseCode(UNAUTHORIZED_CODE)
                        .description(UNAUTHORIZED)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponseDto.class))))

                // 403 Forbiden
                .response(responseBuilder()
                        .responseCode(FORBIDDEN_CODE)
                        .description(FORBIDDEN)
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponseDto.class))))

                // 409 Conflict
                .response(responseBuilder()
                        .responseCode(CONFLICT_CODE)
                        .description("Conflicto al registrar el usuario")
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponseDto.class))))

                // 500 Internal Server Error
                .response(responseBuilder()
                        .responseCode(INTERNAL_ERROR_CODE)
                        .description("Error interno del servidor")
                        .content(contentBuilder().mediaType(MediaType.APPLICATION_JSON_VALUE)
                                .schema(schemaBuilder().implementation(ErrorResponseDto.class))));
    }
}
