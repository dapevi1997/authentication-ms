package co.com.crediya.r2dbc.helper;

import co.com.crediya.model.user.User;
import co.com.crediya.model.user.exception.UserContructionException;
import co.com.crediya.model.user.values.*;
import co.com.crediya.r2dbc.UserEntity;

public class ObjectMapper {
    private ObjectMapper() {
    }

    public static UserEntity userToUserEntity(User user){
        UserEntity userEntity = new UserEntity();
        userEntity.setName(user.getName().getNameUser());
        userEntity.setLastname(user.getLastName().getLastNameUser());
        userEntity.setEmail(user.getEmail().getEmailUser());
        userEntity.setPhone(user.getPhone().getPhoneUser());
        userEntity.setDocumentoIdentidad(user.getDocumentId().getDocumentoIdentidadUsuario());
        userEntity.setBaseSalary(user.getBaseSalary().getBaseSalaryUser());
        userEntity.setIdRol(user.getIdRole());
        return userEntity;
    }

    public static User userEntityToUser(UserEntity userEntity) {
        try {
            User user = new User();
            user.setIdUser(userEntity.getIdUser());
            user.setIdRole(userEntity.getIdRol());
            user.setName(new Name(userEntity.getName()));
            user.setLastName(new LastName(userEntity.getName()));
            user.setDocumentId(new DocumentId(userEntity.getDocumentoIdentidad().toString()));
            user.setPhone(new Phone(userEntity.getPhone().toString()));
            user.setEmail(new Email(userEntity.getEmail()));
            user.setBaseSalary(new BaseSalary(userEntity.getBaseSalary().toString()));
            return user;
        } catch (UserContructionException ex){
            return null;
        }

    }
}
