package co.com.crediya.r2dbc.helper;

import co.com.crediya.model.role.Role;
import co.com.crediya.model.role.values.Description;
import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.ConstructionDomainException;
import co.com.crediya.model.user.values.*;
import co.com.crediya.r2dbc.entity.RoleEntity;
import co.com.crediya.r2dbc.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class CustomMapperR2dbc {
    public static UserEntity userToUserEntity(User user){
        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName().getNameUser());
        userEntity.setLastname(user.getLastName().getLastNameUser());
        userEntity.setBirthdate(user.getBirthday().getUserBirthday());
        userEntity.setEmail(user.getEmail().getEmailUser());
        userEntity.setAddress(user.getAddress().getAdress());
        userEntity.setPhone(user.getPhone().getPhoneUser());
        userEntity.setDocumentoIdentidad(user.getDocumentId().getDocumentoIdentidadUsuario());
        userEntity.setBaseSalary(user.getBaseSalary().getBaseSalaryUser());
        userEntity.setIdRol(user.getIdRole().getIdRole());
        userEntity.setPassword(user.getPassword().getPassword());
        userEntity.setCreatedAt(user.getCreatedAt().getCreatedAt());
        return userEntity;
    }

    public static RoleEntity roleToRoleEntity(Role role){
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setName(role.getNameRole().getNameRole());
        roleEntity.setDescription(role.getDescriptionRole().getDescriptionRole());

        return roleEntity;
    }

    public static User userEntityToUser(UserEntity userEntity) throws ConstructionDomainException {
            User user = new User();
            user.setIdUser(new IdUser(userEntity.getIdUser().toString()));
            user.setIdRole(new IdRole(userEntity.getIdRol().toString()));
            user.setName(new Name(userEntity.getName()));
            user.setLastName(new LastName(userEntity.getName()));
            user.setBirthday(new Birthday(userEntity.getBirthdate().toString()));
            user.setAddress(new Address(userEntity.getAddress()));
            user.setDocumentId(new DocumentId(userEntity.getDocumentoIdentidad().toString()));
            user.setPhone(new Phone(userEntity.getPhone().toString()));
            user.setEmail(new Email(userEntity.getEmail()));
            user.setBaseSalary(new BaseSalary(userEntity.getBaseSalary().toString()));
            user.setPassword(new Password(userEntity.getPassword()));
            user.setCreatedAt(new CreatedAt(userEntity.getCreatedAt().toString()));
            return user;
    }

    public static Role roleEntityToRole(RoleEntity roleEntity) throws ConstructionDomainException {
        Role role = new Role();
        role.setNameRole(new co.com.crediya.model.role.values.Name(roleEntity.getName()));
        role.setDescriptionRole(new Description(roleEntity.getDescription()));
        role.setIdRole(new co.com.crediya.model.role.values.IdRole(roleEntity.getIdRole().toString()));
        return role;
    }
}
