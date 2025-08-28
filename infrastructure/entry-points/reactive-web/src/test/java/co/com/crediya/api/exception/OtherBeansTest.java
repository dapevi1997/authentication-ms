package co.com.crediya.api.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class OtherBeansTest {

    @Test
    void testDefaultStatusBean() {
        OtherBeans otherBeans = new OtherBeans();
        HttpStatus defaultStatus = otherBeans.defaultStatus();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, defaultStatus);
        assertEquals(500, defaultStatus.value());
    }

    @Test
    void testDefaultStatusIsCorrectInstance() {
        OtherBeans otherBeans = new OtherBeans();
        HttpStatus status = otherBeans.defaultStatus();

        assertNotNull(status);
        assertTrue(status.is5xxServerError());
    }
}
