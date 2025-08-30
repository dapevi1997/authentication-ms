package co.com.crediya.api.util;

import co.com.crediya.api.security.UserPrincipal;
import co.com.crediya.api.security.util.JwtProperties;
import co.com.crediya.api.security.util.JwtService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {
    private static final JwtProperties properties = new JwtProperties(
            "a-string-secret-at-least-256-bits-long",
            3600L
    );

    private static JwtService jwtService;

    @BeforeAll
    static void setUp() {
        jwtService= new JwtService(properties);
    }

    @Test
    @DisplayName("Generate Token Success")
    void generateTokenSuccess() {
        // Arrange
        UserPrincipal userPrincipal = UserPrincipal.builder()
                .email("mail@mail,com")
                .password("password")
                .build();
        // Act
        String generatedToken = jwtService.generateToken(userPrincipal);

        // Assert
        assertNotNull(generatedToken);
    }

    @Test
    @DisplayName("Extract Username Success")
    void extractUsernameSuccess() {
        // Arrange
        UserPrincipal userPrincipal = UserPrincipal.builder()
                .email("mail@mail,com")
                .password("password")
                .build();
        String token = jwtService.generateToken(userPrincipal);

        // Act
        String extractedUsername = jwtService.extractUsername(token);

        // Assert
        assertEquals("mail@mail,com", extractedUsername);
    }

    @Test
    @DisplayName("Extract Claims")
    void extractClaims() {
        // Arrange
        UserPrincipal userPrincipal = UserPrincipal.builder()
                .email("mail@mail,com")
                .password("password")
                .build();

        String token = jwtService.generateToken(userPrincipal);

        // Act
        String extractedUsername = jwtService.extractUsername(token);

        // Assert
        assertEquals("mail@mail,com", extractedUsername);
    }
}