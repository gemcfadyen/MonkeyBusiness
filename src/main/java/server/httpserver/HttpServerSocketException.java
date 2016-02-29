package server.httpserver;

public class HttpServerSocketException extends RuntimeException {
    public HttpServerSocketException(String message,  Throwable e) {
        super(message, e);
    }
}
