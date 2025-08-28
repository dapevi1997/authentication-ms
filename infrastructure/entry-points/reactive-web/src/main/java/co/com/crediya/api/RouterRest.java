package co.com.crediya.api;

import co.com.crediya.api.swaggerutil.OpenAPIDocs;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;

@Configuration
public class RouterRest {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route().POST("/api/v1/usuarios", accept(MediaType.APPLICATION_JSON), handler::registerUser, ops -> ops.beanClass(OpenAPIDocs.class).beanMethod("registerUserOperation")).build();
    }
}
