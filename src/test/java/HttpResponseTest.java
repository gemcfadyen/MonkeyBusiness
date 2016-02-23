import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HttpResponseTest {

    private HttpResponse responseThrowingException;

    @Before
    public void setUp() throws Exception {
        responseThrowingException = new HttpResponse(200, "HTTP/1.1", "OK") {
            protected byte[] formatResponse() throws UnsupportedEncodingException {
                throw new UnsupportedEncodingException("Exception Thrown For Test");
            }
        };
    }

    @Test
    public void httpResponseFormat() {
        HttpResponse response = new HttpResponse(200, "HTTP/1.1", "OK");

        byte[] formattedResponse = response.formatForClient();

        assertThat(formattedResponse, is("HTTP/1.1 200 OK\r\n\r\n".getBytes()));
    }

    @Test(expected = HttpResponseException.class)
    public void exceptionIsThrownWhenErrorInFormatting() throws IOException {
        responseThrowingException.formatForClient();
    }

    @Test
    public void exceptionOnFormattingHasMessageAndCause() {
        RuntimeException caughtException = null;
        try {
            responseThrowingException.formatForClient();
        } catch (Exception e) {
          caughtException = (RuntimeException) e;
        }

        assertThat(caughtException.getMessage(), is("Error in creating HTTP Response"));
        assertThat(caughtException.getCause(), CoreMatchers.instanceOf(UnsupportedEncodingException.class));
    }
}
