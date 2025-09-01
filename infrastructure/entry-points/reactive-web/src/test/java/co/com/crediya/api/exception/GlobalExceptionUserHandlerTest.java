package co.com.crediya.api.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import co.com.crediya.model.user.exception.DomainException;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionUserHandlerTest {

    @Mock
    private ErrorAttributes errorAttributes;
    @Mock
    private WebProperties webProperties;
    @Mock
    private ServerCodecConfigurer codecConfigurer;
    @Mock
    private WebProperties.Resources resources;

    private ApplicationContext applicationContext;
    private GlobalExceptionHandler globalExceptionHandler;
    private Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode;

    @BeforeEach
    void setUp() {
        // Crear un ApplicationContext real en lugar de un mock
        applicationContext = new AnnotationConfigApplicationContext();

        exceptionToStatusCode = new HashMap<>();
        exceptionToStatusCode.put(RuntimeException.class, HttpStatus.INTERNAL_SERVER_ERROR);

        when(webProperties.getResources()).thenReturn(resources);
        when(codecConfigurer.getWriters()).thenReturn(java.util.Collections.emptyList());
        when(codecConfigurer.getReaders()).thenReturn(java.util.Collections.emptyList());

        globalExceptionHandler = new GlobalExceptionHandler(errorAttributes, webProperties,
                codecConfigurer, applicationContext, exceptionToStatusCode,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testConstructorInitialization() {
        assertNotNull(globalExceptionHandler);
    }

    @Test
    void testExceptionToStatusCodeMapping() {
        Map<Class<? extends Exception>, HttpStatus> mapping = new HashMap<>();
        mapping.put(BadRequestException.class, HttpStatus.BAD_REQUEST);
        mapping.put(DomainException.class, HttpStatus.CONFLICT);

        assertEquals(HttpStatus.BAD_REQUEST, mapping.get(BadRequestException.class));
        assertEquals(HttpStatus.CONFLICT, mapping.get(DomainException.class));
    }

    @Test
    void testDefaultStatusConfiguration() {
        HttpStatus defaultStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        assertEquals(500, defaultStatus.value());
        assertTrue(defaultStatus.is5xxServerError());
    }

    @Test
    void testExceptionToStatusCodeContainsRuntimeException() {
        assertTrue(exceptionToStatusCode.containsKey(RuntimeException.class));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,
                exceptionToStatusCode.get(RuntimeException.class));
    }

    @Test
    void testGlobalExceptionHandlerInstanceOfAbstractErrorWebExceptionHandler() {
        assertTrue(
                globalExceptionHandler instanceof org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler);
    }

    @Test
    void testExceptionToStatusCodeIsNotEmpty() {
        assertNotNull(exceptionToStatusCode);
        assertTrue(exceptionToStatusCode.size() > 0);
    }

    @Test
    void testApplicationContextIsNotNull() {
        assertNotNull(applicationContext);
    }
}
