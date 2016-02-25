package server;

public class HttpResponseException extends RuntimeException {
    public HttpResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
