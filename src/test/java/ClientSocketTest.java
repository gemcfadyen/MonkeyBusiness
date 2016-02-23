import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientSocketTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
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

    @Test
    public void exceptionThrownWhenErrorInGettingResponse() {
        expectedException.expect(ClientSocketException.class);
        expectedException.expectMessage("Exception in socket whilst retrieving the request");
        expectedException.expectCause(IsInstanceOf.<Throwable>instanceOf(IOException.class));

        clientSocket.getRawHttpRequest();
    }

    @Test
    public void exceptionThrownWhenErrorInClosingSocket() {
        expectedException.expect(ClientSocketException.class);
        expectedException.expectMessage("Exception whilst closing socket");
        expectedException.expectCause(IsInstanceOf.<Throwable>instanceOf(IOException.class));

        clientSocket.close();
    }

    @Test
    public void exceptionThrownWhenErrorInSettingResponse() {
        expectedException.expect(ClientSocketException.class);
        expectedException.expectMessage("Exception whilst writing request to socket");
        expectedException.expectCause(IsInstanceOf.<Throwable>instanceOf(IOException.class));

        clientSocket.setHttpResponse(new HttpResponse(200, "HTTP/1.1", "OK"));
    }
}
