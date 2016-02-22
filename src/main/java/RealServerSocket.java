import java.io.IOException;
import java.net.ServerSocket;

public class RealServerSocket implements HttpServerSocket {
    private final ServerSocket serverSocket;

    public RealServerSocket(ServerSocket serverSocket) {
       this.serverSocket = serverSocket;
    }

    @Override
    public HttpSocket accept() {
        try {
            return new ClientSocket(serverSocket.accept());
        } catch (IOException e) {
            e.printStackTrace(); //TODO custom exception here
            throw new RuntimeException(e);
        }
    }
}



