package co.com.crediya.api.route;

import co.com.crediya.api.handler.AuthHandler;
import co.com.crediya.api.openapiutil.AuthOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class AuthRouterRest {

    @Bean
    public RouterFunction<ServerResponse> authRouterFunction(AuthHandler authHandler) {
        return route().POST("/api/v1/login", accept(MediaType.APPLICATION_JSON), authHandler::login, AuthOpenApi::login)
                .build();
    }
}
