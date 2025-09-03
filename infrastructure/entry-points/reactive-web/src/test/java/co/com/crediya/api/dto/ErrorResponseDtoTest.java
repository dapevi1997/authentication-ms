package co.com.crediya.api.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorResponseDtoTest {

    @Test
    void testBuilderCreatesObject() {
        ErrorResponseDto dto = ErrorResponseDto.builder()
                .httpStatus("BAD_REQUEST")
                .message("Invalid data")
                .build();

        assertThat(dto.getHttpStatus()).isEqualTo("BAD_REQUEST");
        assertThat(dto.getMessage()).isEqualTo("Invalid data");
        assertThat(dto.getTimestamp()).isNotNull();
    }

    @Test
    void testAllArgsConstructor() {
        String ts = "123456789";
        ErrorResponseDto dto = new ErrorResponseDto("CONFLICT", "Already exists", ts);

        assertThat(dto.getHttpStatus()).isEqualTo("CONFLICT");
        assertThat(dto.getMessage()).isEqualTo("Already exists");
        assertThat(dto.getTimestamp()).isEqualTo(ts);
    }

    @Test
    void testEqualsAndHashCode() {
        ErrorResponseDto dto1 = ErrorResponseDto.builder()
                .httpStatus("ERROR")
                .message("fail")
                .build();

        ErrorResponseDto dto2 = ErrorResponseDto.builder()
                .httpStatus("ERROR")
                .message("fail")
                .timestamp(dto1.getTimestamp()) // asegurar igualdad
                .build();

        assertThat(dto1).isEqualTo(dto2);
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
    }

    @Test
    void testToStringContainsFields() {
        ErrorResponseDto dto = ErrorResponseDto.builder()
                .httpStatus("OK")
                .message("success")
                .build();

        String dtoString = dto.toString();

        assertThat(dtoString).contains("OK");
        assertThat(dtoString).contains("success");
        assertThat(dtoString).contains("timestamp");
    }
}
