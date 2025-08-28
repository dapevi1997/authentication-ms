package co.com.crediya.model.user.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class DomainExceptionTest {
    @Test
    void domainExceptionMessage() {
        String msg = "error";
        DomainException ex = new DomainException(msg);
        assertEquals(msg, ex.getMessage());
    }
}
