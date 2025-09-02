package co.com.crediya.api.route;

import co.com.crediya.api.config.AuthPath;
import co.com.crediya.api.handler.AuthHandler;
import co.com.crediya.api.openapiutil.AuthOpenApi;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
@RequiredArgsConstructor
public class AuthRouterRest {
    private final AuthPath authPath;

    @Bean
    public RouterFunction<ServerResponse> authRouterFunction(AuthHandler authHandler) {
        return route().POST(authPath.getLogin(), accept(MediaType.APPLICATION_JSON), authHandler::login, AuthOpenApi::login)
                .build();
    }
}
