package co.com.crediya.api.security;

import co.com.crediya.api.security.util.JwtService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class JwtAuthenticationManagerTest {

    private JwtService jwtService;
    private JwtAuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        jwtService = Mockito.mock(JwtService.class);
        authenticationManager = new JwtAuthenticationManager(jwtService);
    }

    @Test
    void authenticate_ShouldReturnAuthentication_WhenTokenIsValid() {
        // Arrange
        String token = "valid.jwt.token";
        Claims claims = mock(Claims.class);

        when(jwtService.extractAllClaims(token)).thenReturn(claims);
        when(claims.getSubject()).thenReturn("user@mail.com");
        when(claims.get("roles", List.class)).thenReturn(List.of("ROLE_USER", "ROLE_ADMIN"));

        Authentication authRequest =
                new UsernamePasswordAuthenticationToken(null, token);

        // Act
        Mono<Authentication> result = authenticationManager.authenticate(authRequest);

        // Assert
        StepVerifier.create(result)
                .assertNext(auth -> {
                    assertThat(auth).isInstanceOf(UsernamePasswordAuthenticationToken.class);
                    assertThat(auth.getPrincipal()).isEqualTo("user@mail.com");
                    assertThat(auth.getCredentials()).isNull();
                })
                .verifyComplete();

        verify(jwtService, times(1)).extractAllClaims(token);
    }

    @Test
    void authenticate_ShouldError_WhenTokenIsInvalid() {
        // Arrange
        String token = "invalid.jwt.token";
        Authentication authRequest =
                new UsernamePasswordAuthenticationToken(null, token);

        when(jwtService.extractAllClaims(token))
                .thenThrow(new RuntimeException("Invalid JWT"));

        // Act
        Mono<Authentication> result = authenticationManager.authenticate(authRequest);

        // Assert
        StepVerifier.create(result)
                .expectErrorSatisfies(error ->
                        assertThat(error)
                                .isInstanceOf(BadCredentialsException.class)
                                .hasMessage("Invalid Token")
                )
                .verify();

        verify(jwtService, times(1)).extractAllClaims(token);
    }

    @Test
    void authenticate_ShouldReturnEmptyAuthorities_WhenNoRolesInToken() {
        // Arrange
        String token = "jwt.without.roles";
        Claims claims = mock(Claims.class);

        when(jwtService.extractAllClaims(token)).thenReturn(claims);
        when(claims.getSubject()).thenReturn("noRolesUser@mail.com");
        when(claims.get("roles", List.class)).thenReturn(List.of());

        Authentication authRequest =
                new UsernamePasswordAuthenticationToken(null, token);

        // Act
        Mono<Authentication> result = authenticationManager.authenticate(authRequest);

        // Assert
        StepVerifier.create(result)
                .assertNext(auth -> {
                    assertThat(auth.getPrincipal()).isEqualTo("noRolesUser@mail.com");
                    assertThat(auth.getAuthorities()).isEmpty();
                })
                .verifyComplete();
    }
}
