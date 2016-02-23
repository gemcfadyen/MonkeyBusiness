import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HttpServerSocketExceptionTest {
    private IOException originalException;
    private HttpServerSocketException exception;

    @Before
    public void setUp() throws Exception {
        originalException = new IOException();
        exception = new HttpServerSocketException("Error in ServerSocket", originalException);
    }

    @Test
    public void httpServerSocketExceptionHasMessage() {
        assertThat(exception.getMessage(), is("Error in ServerSocket"));
    }

    @Test
    public void httpServerSocketExceptionCause() {
        assertThat(exception.getCause(), is(originalException));
    }
}
