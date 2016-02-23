import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HttpServerSocketTest {

    private ServerSocket fakeServerSocket;
    private HttpServerSocket httpServerSocket;

    @Before
    public void setUp() throws Exception {
        fakeServerSocket = new ServerSocket() {
            @Override
            public Socket accept() throws IOException {
                throw new IOException("Throws exception for test");
            }
        };
        httpServerSocket = new HttpServerSocket(fakeServerSocket);
    }

    @Test(expected = HttpServerSocketException.class)
    public void serverThrowsException() throws IOException {
        httpServerSocket.accept();
    }

    @Test
    public void exceptionThrownByServerHasMessageAndCause() throws IOException {
        RuntimeException caughtException = null;
        try {
            httpServerSocket.accept();
        } catch (Exception e) {
            caughtException = (RuntimeException) e;
        }

        assertThat(caughtException.getMessage(), is("Exception occurred whilst server was accepting client requests"));
        assertThat(caughtException.getCause(), instanceOf(IOException.class));
    }
}

