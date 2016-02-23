import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HttpResponseTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private HttpResponse responseThrowingException;

    @Before
    public void setUp() throws Exception {
        responseThrowingException = new HttpResponse(200, "HTTP/1.1", "OK", Collections.emptyList()) {
            protected byte[] formatResponse() throws UnsupportedEncodingException {
                throw new UnsupportedEncodingException("Exception Thrown For Test");
            }
        };
    }

    @Test
    public void statusLineResponseFormat() {
        HttpResponse response = new HttpResponse(200, "HTTP/1.1", "OK", Collections.emptyList());

        byte[] formattedResponse = response.formatForClient();

        assertThat(formattedResponse, is("HTTP/1.1 200 OK\r\n\r\n".getBytes()));
    }

    @Test
    public void statusLineWithAllowMethodsResponseFormat() {
        HttpResponse response = HttpResponseBuilder.anHttpResponseBuilder().withStatus(200).withReasonPhrase("OK").withAllowMethods("GET", "PUT").build();

        byte[] formattedResponse = response.formatForClient();

        assertThat(formattedResponse, is("HTTP/1.1 200 OK\r\nAllow: GET,PUT\r\n\r\n".getBytes()));
    }

    @Test
    public void allowMethods() {
        HttpResponse response = HttpResponseBuilder.anHttpResponseBuilder().withStatus(200).withReasonPhrase("OK").withAllowMethods("GET", "POST").build();

        List<String> allowedMethods = response.allowedMethods();

        assertThat(allowedMethods.containsAll(Arrays.asList("GET", "POST")), is(true));
    }

    @Test
    public void exceptionOnFormatting() {
        expectedException.expect(HttpResponseException.class);
        expectedException.expectMessage("Error in creating HTTP Response");
        expectedException.expectCause(IsInstanceOf.<Throwable>instanceOf(UnsupportedEncodingException.class));

        responseThrowingException.formatForClient();
    }
}
