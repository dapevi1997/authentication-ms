package co.com.crediya.config;

import java.io.Serializable;

public class ErrorResponseDto implements Serializable {
    private String message;

    public ErrorResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
