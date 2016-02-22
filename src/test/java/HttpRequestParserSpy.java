import java.io.InputStream;

public class HttpRequestParserSpy implements HttpRequestParser {
    private boolean hasParsedRequest = false;

    @Override
    public HttpRequest parse(InputStream inputStream) {
        hasParsedRequest = true;
        return null;
    }

    public boolean hasParsedRequest() {
        return hasParsedRequest;
    }
}
