package co.com.crediya.api.dto;

import java.io.Serializable;

public class RegisterUserResponseDto implements Serializable {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
