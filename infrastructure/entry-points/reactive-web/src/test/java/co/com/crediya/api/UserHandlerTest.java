package co.com.crediya.api;

import co.com.crediya.api.dto.RegisterUserRequestDto;
import co.com.crediya.api.exception.BadRequestException;
import co.com.crediya.api.util.CustomMapperWebFlux;
import co.com.crediya.model.role.Role;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.exception.DomainException;
import co.com.crediya.model.user.values.*;
import co.com.crediya.requestvalidator.RequestValidator;
import co.com.crediya.usecase.getallroles.GetAllRolesUseCase;
import co.com.crediya.usecase.registeruser.RegisterUserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserHandlerTest {

    @Mock
    private RequestValidator requestValidator;
    @Mock
    private RegisterUserUseCase registerUserUseCase;
    @Mock
    private GetAllRolesUseCase getAllRolesUseCase;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private CustomMapperWebFlux customMapper;
    @Mock
    private ServerRequest serverRequest;

    @InjectMocks
    private UserHandler userHandler;

    private RegisterUserRequestDto validRequestDto;
    private User validUser;
    private Role validRole;

    @BeforeEach
    void setUp() throws ConstructionDomainException {
        validRequestDto = new RegisterUserRequestDto();
        validRequestDto.setName("Juan");
        validRequestDto.setLastName("Perez");
        validRequestDto.setEmail("juan@test.com");
        validRequestDto.setUserBirthday("1990-01-01");
        validRequestDto.setAddress("Calle 123");
        validRequestDto.setDocumentId("12345678");
        validRequestDto.setBaseSalary("1000.00");
        validRequestDto.setIdRole("1");
        validRequestDto.setPhone("3001234567");

        validUser = new User();
        validUser.setName(new Name("Juan"));
        validUser.setLastName(new LastName("Perez"));
        validUser.setEmail(new Email("juan@test.com"));
        validUser.setBirthday(new Birthday("1990-01-01"));
        validUser.setAddress(new Address("Calle 123"));
        validUser.setDocumentId(new DocumentId("12345678"));
        validUser.setBaseSalary(new BaseSalary("1000.00"));
        validUser.setIdRole(new IdRole("1"));
        validUser.setPhone(new Phone("3001234567"));
        validUser.setIdUser(new IdUser("1"));

        validRole = new Role();
        validRole.setIdRole(new co.com.crediya.model.role.values.IdRole("1"));
    }

    @Test
    void registerUserSuccess() throws ConstructionDomainException {
        when(serverRequest.bodyToMono(RegisterUserRequestDto.class))
                .thenReturn(Mono.just(validRequestDto));
        when(requestValidator.validate(any())).thenReturn(Collections.emptyList());
        when(customMapper.registerUserRequestDtoToUser(any())).thenReturn(validUser);
        when(getAllRolesUseCase.getAllRoles()).thenReturn(Flux.just(validRole));
        when(registerUserUseCase.registerUser(any())).thenReturn(Mono.just(validUser));

        StepVerifier.create(userHandler.registerUser(serverRequest))
                .expectNextMatches(response -> response instanceof ServerResponse).verifyComplete();

        verify(requestValidator).validate(validRequestDto);
        verify(customMapper).registerUserRequestDtoToUser(validRequestDto);
        verify(registerUserUseCase).registerUser(validUser);
    }

    @Test
    void registerUserValidationError() {
        when(serverRequest.bodyToMono(RegisterUserRequestDto.class))
                .thenReturn(Mono.just(validRequestDto));
        when(requestValidator.validate(any())).thenReturn(List.of("Error de validación"));

        StepVerifier.create(userHandler.registerUser(serverRequest))
                .expectError(BadRequestException.class).verify();

        verify(requestValidator).validate(validRequestDto);
        verifyNoInteractions(customMapper);
        verifyNoInteractions(registerUserUseCase);
    }

    @Test
    void registerUserDomainConstructionError() throws ConstructionDomainException {
        when(serverRequest.bodyToMono(RegisterUserRequestDto.class))
                .thenReturn(Mono.just(validRequestDto));
        when(requestValidator.validate(any())).thenReturn(Collections.emptyList());
        when(customMapper.registerUserRequestDtoToUser(any()))
                .thenThrow(new ConstructionDomainException("Error de construcción"));

        StepVerifier.create(userHandler.registerUser(serverRequest))
                .expectError(ConstructionDomainException.class).verify();

        verify(requestValidator).validate(validRequestDto);
        verify(customMapper).registerUserRequestDtoToUser(validRequestDto);
        verifyNoInteractions(registerUserUseCase);
    }

    @Test
    void registerUserRoleNotFound() throws ConstructionDomainException {
        when(serverRequest.bodyToMono(RegisterUserRequestDto.class))
                .thenReturn(Mono.just(validRequestDto));
        when(requestValidator.validate(any())).thenReturn(Collections.emptyList());
        when(customMapper.registerUserRequestDtoToUser(any())).thenReturn(validUser);
        when(getAllRolesUseCase.getAllRoles()).thenReturn(Flux.empty());

        StepVerifier.create(userHandler.registerUser(serverRequest)).expectError(DomainException.class)
                .verify();

        verify(requestValidator).validate(validRequestDto);
        verify(customMapper).registerUserRequestDtoToUser(validRequestDto);
        verify(getAllRolesUseCase).getAllRoles();
        verifyNoInteractions(registerUserUseCase);
    }
}
