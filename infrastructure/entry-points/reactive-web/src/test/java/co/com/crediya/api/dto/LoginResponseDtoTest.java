package co.com.crediya.api.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoginResponseDtoTest {

    @Test
    void testBuilderCreatesObject() {
        LoginResponseDto dto = LoginResponseDto.builder()
                .token("jwt-token-123")
                .build();

        assertThat(dto.getToken()).isEqualTo("jwt-token-123");
    }

    @Test
    void testSetterAndGetter() {
        LoginResponseDto dto = new LoginResponseDto();
        dto.setToken("new-token");

        assertThat(dto.getToken()).isEqualTo("new-token");
    }

    @Test
    void testEqualsAndHashCode() {
        LoginResponseDto dto1 = LoginResponseDto.builder()
                .token("abc")
                .build();

        LoginResponseDto dto2 = LoginResponseDto.builder()
                .token("abc")
                .build();

        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    void testToStringContainsToken() {
        LoginResponseDto dto = LoginResponseDto.builder()
                .token("xyz")
                .build();

        assertThat(dto.toString()).contains("xyz");
    }
}
