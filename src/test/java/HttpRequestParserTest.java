import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HttpRequestParserTest {

    private InputStream getRequestInputStream;

    @Before
    public void setupFixture() {

        String getRequest = "GET / HTTP/1.1\r\n" +
                "Host: localhost:5000\r\n" +
                "Connection: Keep-Alive\r\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\n" +
                "Accept-Encoding: gzip,deflate\r\n\r\n";
        getRequestInputStream = new ByteArrayInputStream(getRequest.getBytes());
    }

    @Test
    public void parsesMethodFromRequest() {
        //Method SP Request-URI SP HTTP-Version CRLF
        HttpRequestParser parser = new HttpRequestParser();
        HttpRequest request = parser.parse(getRequestInputStream);

        assertThat(request.getMethod(), is("GET"));
    }

    @Test(expected = HttpRequestParsingException.class)
    public void throwsExceptionIfHeaderCannotBeParsed() {
        HttpRequestParser parser = new HttpRequestParser();
        BufferedReaderWhichThrowsExceptionOnReadLine readerToThrowException = new BufferedReaderWhichThrowsExceptionOnReadLine(new StringReader("hi"));
        parser.parseRequest(readerToThrowException);
    }

    @Test
    public void parsesUriFromRequest() {
        HttpRequestParser parser = new HttpRequestParser();
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
