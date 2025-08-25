package co.com.crediya.api.dto;

import java.io.Serializable;

public class RegisterUserResponseDto implements Serializable {
    private String idUser;
    private String idRole;
    private String message;
    private final String timestamp;

    public RegisterUserResponseDto() {
        this.timestamp = String.valueOf(System.currentTimeMillis());
    }

    public RegisterUserResponseDto(String idUser, String idRole, String message) {
        this.idUser = idUser;
        this.idRole = idRole;
        this.message = message;
        this.timestamp = String.valueOf(System.currentTimeMillis());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIdRole() {
        return idRole;
    }

    public void setIdRole(String idRole) {
        this.idRole = idRole;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
