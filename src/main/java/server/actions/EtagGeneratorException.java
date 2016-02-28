package server.actions;

public class EtagGeneratorException extends RuntimeException {
    public EtagGeneratorException(String reason, Throwable cause) {
        super(reason, cause);

    }
}
