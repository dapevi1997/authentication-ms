package co.com.crediya.api.util;

import co.com.crediya.api.dto.RegisterUserRequestDto;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.values.*;
import org.springframework.stereotype.Component;

@Component
public class CustomMapperWebFlux {
    public User registerUserRequestDtoToUser(RegisterUserRequestDto dto) throws ConstructionDomainException {
        User user = new User();
        user.setName(new Name(dto.getName()));
        user.setLastName(new LastName(dto.getLastName()));
        user.setEmail(new Email(dto.getEmail()));
        user.setBirthday(new Birthday(dto.getUserBirthday()));
        user.setAddress(new Address(dto.getAddress()));
        user.setDocumentId(new DocumentId(dto.getDocumentId()));
        user.setBaseSalary(new BaseSalary(dto.getBaseSalary()));
        user.setIdRole(new IdRole(dto.getIdRole()));
        user.setPhone(new Phone(dto.getPhone()));
        return user;
    }
}
