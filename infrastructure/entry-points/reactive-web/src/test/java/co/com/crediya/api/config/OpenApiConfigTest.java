package co.com.crediya.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OpenApiConfigTest {

    @Test
    void testCustomOpenAPI_InfoSection() {
        OpenApiConfig config = new OpenApiConfig();
        OpenAPI openAPI = config.customOpenAPI();

        Info info = openAPI.getInfo();

        assertThat(info).isNotNull();
        assertThat(info.getTitle()).isEqualTo("API microservicio AUTHENTICATION");
        assertThat(info.getVersion()).isEqualTo("1.0");
        assertThat(info.getDescription()).contains("autenticación y autorización");
        assertThat(info.getContact()).isNotNull();
        assertThat(info.getContact().getName()).isEqualTo("DANIEL PEREZ VITOLA");
        assertThat(info.getContact().getEmail()).isEqualTo("dapevi97@gmail.com");
    }

    @Test
    void testCustomOpenAPI_SecurityConfiguration() {
        OpenApiConfig config = new OpenApiConfig();
        OpenAPI openAPI = config.customOpenAPI();

        Components components = openAPI.getComponents();

        assertThat(components).isNotNull();
        assertThat(components.getSecuritySchemes()).containsKey("bearerAuth");

        SecurityScheme scheme = components.getSecuritySchemes().get("bearerAuth");

        assertThat(scheme).isNotNull();
        assertThat(scheme.getType()).isEqualTo(SecurityScheme.Type.HTTP);
        assertThat(scheme.getScheme()).isEqualTo("bearer");
        assertThat(scheme.getBearerFormat()).isEqualTo("JWT");

        // Validar que se añadió el requerimiento global
        assertThat(openAPI.getSecurity()).isNotEmpty();
        assertThat(openAPI.getSecurity().get(0).get("bearerAuth")).isNotNull();
    }
}
