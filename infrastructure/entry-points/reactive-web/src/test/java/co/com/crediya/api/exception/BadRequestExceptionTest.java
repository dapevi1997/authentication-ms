package co.com.crediya.api.exception;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class BadRequestExceptionTest {

    @Test
    void testExceptionMessageIsStored() {
        String message = "Invalid request data";
        BadRequestException exception = new BadRequestException(message);

        assertThat(exception).isInstanceOf(RuntimeException.class);
        assertThat(exception.getMessage()).isEqualTo(message);
    }

    @Test
    void testExceptionWithoutNullMessage() {
        BadRequestException exception = new BadRequestException(null);

        assertThat(exception.getMessage()).isNull();
    }
}
