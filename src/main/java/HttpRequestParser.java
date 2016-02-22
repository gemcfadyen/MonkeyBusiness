import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequestParser implements RequestParser {
    @Override
    public HttpRequest parse(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return parseRequest(reader);
    }

    protected HttpRequest parseRequest(BufferedReader reader) {
        String[] requestLine;
        try {
            requestLine = parseRequestLine(readLine(reader));
            return new HttpRequest(getMethod(requestLine), getRequestUri(requestLine));
        } catch (IOException e) {
            e.printStackTrace();
            throw new HttpRequestParsingException();
        }
    }

    private String[] parseRequestLine(String line) {
        return line.split(space());
    }

    private String getRequestUri(String[] requestLine) {
        return requestLine[1];
    }

    private String getMethod(String[] requestLine) {
        return requestLine[0];
    }

    private String space() {
        return " ";
    }

    private String readLine(BufferedReader reader) throws IOException {
        return reader.readLine();
    }
}
