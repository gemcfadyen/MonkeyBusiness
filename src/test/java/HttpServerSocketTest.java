import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServerSocketTest {

    @Test(expected = HttpServerSocketException.class)
    public void serverThrowsException() throws IOException {

        ServerSocket fakeServerSocket = new ServerSocket() {
            @Override
            public Socket accept() throws IOException {
                throw new IOException("Throws exception for test");
            }
        };

        HttpServerSocket httpServerSocket = new HttpServerSocket(fakeServerSocket);
        httpServerSocket.accept();
    }
}

