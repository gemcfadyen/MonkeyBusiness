import java.io.IOException;
import java.net.ServerSocket;

public class HttpServerSocket {
    private final ServerSocket serverSocket;

    public HttpServerSocket(ServerSocket serverSocket) {
       this.serverSocket = serverSocket;
    }

    public HttpSocket accept() {
        try {
            return new ClientSocket(serverSocket.accept());
        } catch (IOException e) {
            e.printStackTrace();
            throw new HttpServerSocketException("Exception occurred whilst server was accepting client requests", e);
        }
    }
}


