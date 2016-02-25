package server;

public class HttpServerSocketException extends RuntimeException {
    public HttpServerSocketException(String message,  Throwable e) {
        super(message, e);
    }
}
