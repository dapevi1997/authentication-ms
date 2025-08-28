package co.com.crediya.api.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class BadRequestExceptionTest {

    @Test
    void testExceptionMessage() {
        String errorMessage = "Validation error";
        BadRequestException exception = new BadRequestException(errorMessage);

        assertEquals(errorMessage, exception.getMessage());
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void testExceptionWithNullMessage() {
        BadRequestException exception = new BadRequestException(null);
        assertNull(exception.getMessage());
    }

    @Test
    void testExceptionWithEmptyMessage() {
        String emptyMessage = "";
        BadRequestException exception = new BadRequestException(emptyMessage);
        assertEquals(emptyMessage, exception.getMessage());
    }
}
