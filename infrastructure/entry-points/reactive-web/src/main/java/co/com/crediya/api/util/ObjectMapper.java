package co.com.crediya.api.util;

import co.com.crediya.api.dto.RegisterUserRequestDto;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.UserContructionException;
import co.com.crediya.model.user.values.*;

public class ObjectMapper {
    private ObjectMapper() {
    }

    public static User registerUserRequestDtoToUser(RegisterUserRequestDto dto) throws UserContructionException {
        return new User(
                new Name(dto.getName()),
                new LastName(dto.getLastName()),
                new Email(dto.getEmail()),
                new DocumentId(dto.getDocumentId()),
                new Phone(dto.getPhone()),
                new BaseSalary(dto.getBaseSalary()),
                Long.valueOf(dto.getIdRole())
        );
    }
}
