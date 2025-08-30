package co.com.crediya.model.logger;

public interface LoggerGateway {
    void info(String message);
    void warn(String message);
    void error(String message, Throwable throwable);
}
