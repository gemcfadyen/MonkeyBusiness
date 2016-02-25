package server;

public class ResourceWriteException extends RuntimeException {
    public ResourceWriteException(String message, Throwable cause) {
       super(message, cause);
    }
}
