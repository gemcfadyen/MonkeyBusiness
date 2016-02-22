import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientSocketTest {

    private Socket fakeSocket;
    private ClientSocket clientSocket;

    @Before
    public void setUp() throws Exception {
        fakeSocket = new Socket() {
            @Override
            public InputStream getInputStream() throws IOException {
                throw new IOException("Exception thrown for testing");
            }

            public synchronized void close() throws IOException {
                throw new IOException("Exception thrown for testing");
            }

            public OutputStream getOutputStream() throws IOException {
                throw new IOException("Exception thrown for testing");
            }
        };
        clientSocket = new ClientSocket(fakeSocket);
    }

    @Test(expected = ClientSocketException.class)
    public void exceptionThrownWhenErrorInGettingRequest() {
        clientSocket.getRawHttpRequest();
    }

    @Test(expected =  ClientSocketException.class)
    public void exceptionThrownWhenSocketCloses() {
        clientSocket.close();
    }

    @Test(expected = ClientSocketException.class)
    public void exceptionThrownWhilstSettingHttpResponse() {
        clientSocket.setHttpResponse(new HttpResponse(200, "HTTP/1.1", "OK"));
    }
}
