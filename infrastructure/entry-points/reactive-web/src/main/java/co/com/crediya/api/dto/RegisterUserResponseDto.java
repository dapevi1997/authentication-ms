package co.com.crediya.api.dto;

import java.io.Serializable;

public class RegisterUserResponseDto implements Serializable {
    private String message;
    private String roleName;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
