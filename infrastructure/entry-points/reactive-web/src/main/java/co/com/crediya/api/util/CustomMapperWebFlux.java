package co.com.crediya.api.util;

import co.com.crediya.api.dto.FindUserByEmailResponseDto;
import co.com.crediya.api.dto.RegisterUserRequestDto;
import co.com.crediya.api.security.UserPrincipal;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.values.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CustomMapperWebFlux {

    public static UserPrincipal userToUserPrincipal(User user) {
        return UserPrincipal
                .builder()
                .email(user.getEmail().getEmailUser())
                .idRole(user.getIdRole().getIdRole())
                .password(user.getPassword().getPassword())
                        .build();

    }

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
        user.setPassword(new Password(dto.getPassword()));
        return user;
    }

    public FindUserByEmailResponseDto userToFindUSerByEmailDto(User user) {
        FindUserByEmailResponseDto findUserByEmailResponseDto = new FindUserByEmailResponseDto();
        findUserByEmailResponseDto.setName(user.getName().getNameUser());
        findUserByEmailResponseDto.setLastName(user.getLastName().getLastNameUser());
        findUserByEmailResponseDto.setEmail(user.getEmail().getEmailUser());
        findUserByEmailResponseDto.setBirthday(user.getBirthday().getUserBirthday());
        findUserByEmailResponseDto.setAddress(user.getAddress().getAdress());
        findUserByEmailResponseDto.setDocumentId(user.getDocumentId().getDocumentoIdentidadUsuario().toString());
        findUserByEmailResponseDto.setBaseSalary(user.getBaseSalary().getBaseSalaryUser());
        findUserByEmailResponseDto.setIdRole(user.getIdRole().getIdRole());
        findUserByEmailResponseDto.setPhone(user.getPhone().getPhoneUser().toString());
        findUserByEmailResponseDto.setIdUser(user.getIdUser().getIdUser());
        return findUserByEmailResponseDto;
    }
}
