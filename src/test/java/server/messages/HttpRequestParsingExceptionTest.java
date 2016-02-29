package server.messages;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HttpRequestParsingExceptionTest {
    private IOException originalException;
    private HttpRequestParsingException exception;

    @Before
    public void setUp() throws Exception {
        originalException = new IOException();
        exception = new HttpRequestParsingException("Error when parsing HTTP request", originalException);
    }

    @Test
    public void httpRequestParsingExceptionHasMessage() {
        assertThat(exception.getMessage(), is("Error when parsing HTTP request"));
    }

    @Test
    public void httpResponseExceptionCause() {
        assertThat(exception.getCause(), is(originalException));

    }
}
