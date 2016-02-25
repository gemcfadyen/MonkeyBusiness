package server;

import java.io.IOException;

public class ClientSocketException extends HttpServerSocketException {
    public ClientSocketException(String message, IOException cause) {
        super(message, cause);
    }
}
