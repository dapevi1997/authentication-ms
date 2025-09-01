package co.com.crediya.api.route;

import co.com.crediya.api.handler.UserHandler;
import co.com.crediya.api.openapiutil.UserOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;

@Configuration
public class UserRouterRest {

    @Bean
    public RouterFunction<ServerResponse> userRouterFunction(UserHandler userHandler) {
        return route().POST("/api/v1/usuarios", accept(MediaType.APPLICATION_JSON), userHandler::registerUser, UserOpenApi::registerUser)
                .build();
    }
}
