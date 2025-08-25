package co.com.crediya.api.swaggerutil;

public class BodyBadRequestResponse {
    private String httpStatus;
    private String message;
    private String timestamp;

    public BodyBadRequestResponse(String message, String httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = String.valueOf(System.currentTimeMillis());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
