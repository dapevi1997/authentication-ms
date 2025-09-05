package co.com.crediya.api.openapiutil;

import org.junit.jupiter.api.Test;
import org.springdoc.core.fn.builders.operation.Builder;

import static org.assertj.core.api.Assertions.assertThat;

class AuthOpenApiTest {

    @Test
    void testLoginBuilderNotNull() {
        Builder builder = Builder.operationBuilder();

        Builder result = AuthOpenApi.login(builder);

        assertThat(result).isNotNull();
        assertThat(result).isSameAs(builder); // fluidez
    }

    @Test
    void testLoginBuilderDoesNotThrow() {
        Builder builder = Builder.operationBuilder();

        // simple validación: que el método se ejecute sin excepciones
        AuthOpenApi.login(builder);
    }
}
