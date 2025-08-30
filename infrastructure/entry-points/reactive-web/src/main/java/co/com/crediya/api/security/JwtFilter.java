package co.com.crediya.api.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Component
public class JwtFilter implements WebFilter {
    // TODO: cambiar de acá o validar
    private static final String[] PUBLIC_PATHS = {
            "/api/v1/login",
            "/swagger-ui",
            "/swagger-docs",
            "/api-docs",
            "/webjars",
            "/actuator/health"
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        //String method = request.getMethod().name();

        // TODO:: se puede mejorar con un estarWith o el contain mejor implementado
        boolean isPublicPath = Arrays.stream(PUBLIC_PATHS)
                .anyMatch(path::contains);

        if (isPublicPath) {
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        // TODO:: verificar si hay que cambiar la exception
        if (authHeader == null) {
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No token provided"));
        }

        if (!authHeader.startsWith("Bearer ")) {
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token format"));
        }

        String token = authHeader.replace("Bearer ", "");
        exchange.getAttributes().put("token", token);
        return chain.filter(exchange);
    }
}
