package server;

public class HttpRequestParsingException extends RuntimeException {

    public HttpRequestParsingException(String message, Throwable cause) {
        super(message, cause);
    }
}
