import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.*;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HttpRequestParserTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private InputStream getRequestInputStream;
    private HttpRequestParser parser;

    @Before
    public void setupFixture() {

        String getRequest = "GET / HTTP/1.1\r\n" +
                "Host: localhost:5000\r\n" +
                "Connection: Keep-Alive\r\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\n" +
                "Accept-Encoding: gzip,deflate\r\n\r\n";
        getRequestInputStream = new ByteArrayInputStream(getRequest.getBytes());
        parser = new HttpRequestParser();
    }

    @Test
    public void parsesMethodFromRequest() {
        //Method SP Request-URI SP HTTP-Version CRLF
        HttpRequest request = parser.parse(getRequestInputStream);

        assertThat(request.getMethod(), is("GET"));
    }

    @Test
    public void exceptionThrownOnParseError() {
        expectedException.expect(HttpRequestParsingException.class);
        expectedException.expectMessage("Error in parsing Http Request");
        expectedException.expectCause(IsInstanceOf.<Throwable>instanceOf(IOException.class));

        BufferedReaderWhichThrowsExceptionOnReadLine readerToThrowException = new BufferedReaderWhichThrowsExceptionOnReadLine(new StringReader("hi"));
        parser.parseRequest(readerToThrowException);
    }

    @Test
    public void parsesUriFromRequest() {
        HttpRequest request = parser.parse(getRequestInputStream);

        assertThat(request.getRequestUri(), is("/"));
    }
}

class BufferedReaderWhichThrowsExceptionOnReadLine extends BufferedReader {

    public BufferedReaderWhichThrowsExceptionOnReadLine(Reader in) {
        super(in);
    }

    public String readLine() throws IOException {
        throw new IOException("Throws Exception for test");
    }
}
