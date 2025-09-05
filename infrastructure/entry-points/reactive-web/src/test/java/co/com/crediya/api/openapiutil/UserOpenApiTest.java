package co.com.crediya.api.openapiutil;

import org.junit.jupiter.api.Test;
import org.springdoc.core.fn.builders.operation.Builder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class UserOpenApiTest {

    @Test
    void testRegisterUserBuilderNotNull() {
        Builder builder = Builder.operationBuilder();

        Builder result = UserOpenApi.registerUser(builder);

        assertThat(result).isNotNull();
        assertThat(result).isSameAs(builder); // fluidez
    }

    @Test
    void testRegisterUserDoesNotThrow() {
        Builder builder = Builder.operationBuilder();

        assertDoesNotThrow(() -> UserOpenApi.registerUser(builder));
    }

    @Test
    void testFindUserByEmailBuilderNotNull() {
        Builder builder = Builder.operationBuilder();

        Builder result = UserOpenApi.findUserByEmail(builder);

        assertThat(result).isNotNull();
        assertThat(result).isSameAs(builder); // fluidez
    }

    @Test
    void testFindUserByEmailDoesNotThrow() {
        Builder builder = Builder.operationBuilder();

        assertDoesNotThrow(() -> UserOpenApi.findUserByEmail(builder));
    }
}
