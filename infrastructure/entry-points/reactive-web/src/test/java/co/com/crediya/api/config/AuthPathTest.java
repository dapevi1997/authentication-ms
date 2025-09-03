package co.com.crediya.api.config;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AuthPathTest {
    @Test
    void testGettersAndSetters() {
        AuthPath authPath = new AuthPath();
        authPath.setLogin("/api/auth/login");

        assertThat(authPath.getLogin()).isEqualTo("/api/auth/login");
    }
}