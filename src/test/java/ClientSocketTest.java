import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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

    @Test
    public void exceptionWhenGettingRequestHasMessageAndCause() {
        RuntimeException caughtException = null;
        try {
            clientSocket.getRawHttpRequest();
        } catch (Exception e) {
            caughtException = (RuntimeException) e;
        }

        assertThat(caughtException.getMessage(), is("Exception in socket whilst retrieving the request"));
        assertThat(caughtException.getCause(), instanceOf(IOException.class));
    }

    @Test(expected = ClientSocketException.class)
    public void exceptionThrownWhenSocketCloses() {
        clientSocket.close();
    }

    @Test
    public void exceptionWhenClosingSocketHasMessageAndCause() {
        RuntimeException caughtException = null;
        try {
            clientSocket.close();
        } catch (Exception e) {
            caughtException = (RuntimeException) e;
        }

        assertThat(caughtException.getMessage(), is("Exception whilst closing socket"));
        assertThat(caughtException.getCause(), instanceOf(IOException.class));
    }

    @Test(expected = ClientSocketException.class)
    public void exceptionThrownWhilstSettingHttpResponse() {
        clientSocket.setHttpResponse(new HttpResponse(200, "HTTP/1.1", "OK"));
    }

    @Test
    public void exceptionWhenSettingHttpResponseHasMessageAndCause() {
        RuntimeException caughtException = null;
        try {
            clientSocket.setHttpResponse(new HttpResponse(200, "HTTP/1.1", "OK"));;
        } catch (Exception e) {
            caughtException = (RuntimeException) e;
        }

        assertThat(caughtException.getMessage(), is("Exception whilst writing request to socket"));
        assertThat(caughtException.getCause(), instanceOf(IOException.class));
    }
}
