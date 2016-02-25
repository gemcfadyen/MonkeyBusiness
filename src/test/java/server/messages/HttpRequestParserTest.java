package server.messages;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.*;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HttpRequestParserTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    private InputStream getRequestInputStream;
    private HttpRequestParser parser;
    private InputStream postRequestInputStream;

    @Before
    public void setupFixture() {

        String getRequest = "GET / HTTP/1.1\r\n" +
                "Host: localhost:5000\r\n" +
                "Connection: Keep-Alive\r\n" +
                "User-Agent: Apache-HttpClient/4.3.5 (java 1.5)\r\n" +
                "Accept-Encoding: gzip,deflate\r\n\r\n";
        getRequestInputStream = new ByteArrayInputStream(getRequest.getBytes());
        parser = new HttpRequestParser();

        String postFixture = "POST /path/script.cgi HTTP/1.1\r\n" +
                "From: frog@jmarshall.com\r\n" +
                "User-Agent: HTTPTool/1.1\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n" +
                "Content-Length: 32\r\n\r\nhome=Cosby&favorite+flavor=flies";
        postRequestInputStream = new ByteArrayInputStream(postFixture.getBytes());
    }

    @Test
    public void parsesMethodFromRequest() {
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

    @Test
    public void parsesMapOfHeaderProperties() {
        HttpRequest request = parser.parse(postRequestInputStream);
        Map<String, String> headerProperties = request.headerParameters();

        assertThat(headerProperties.get("From"), is("frog@jmarshall.com"));
        assertThat(headerProperties.get("User-Agent"), is("HTTPTool/1.1"));
        assertThat(headerProperties.get("Content-Type"), is("application/x-www-form-urlencoded"));
        assertThat(headerProperties.get("Content-Length"), is("32"));
    }

    @Test
    public void parsesBodyFromPostRequest() {
        HttpRequest request = parser.parse(postRequestInputStream);

        assertThat(request.getBody(), is("home=Cosby&favorite+flavor=flies"));
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
