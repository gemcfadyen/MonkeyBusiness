import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestParser implements RequestParser {
    @Override
    public HttpRequest parse(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return parseRequest(reader);
    }

    protected HttpRequest parseRequest(BufferedReader reader) {
        try {
            return createHttpRequest(reader);
        } catch (IOException e) {
            throw new HttpRequestParsingException("Error in parsing Http Request", e);
        }
    }

    private HttpRequest createHttpRequest(BufferedReader reader) throws IOException {
        String[] requestLine = getRequestLine(reader);
        Map<String, String> headerParams = getHeaderParameters(reader);
        char[] bodyContents = getBody(reader, getContentLength(headerParams));
        System.out.println("body is: " + String.valueOf(bodyContents));

        return HttpRequestBuilder.anHttpRequestBuilder()
                .withRequestLine(getMethod(requestLine))
                .withRequestUri(getRequestUri(requestLine))
                .withHeaderParameters(headerParams)
                .withBody(String.valueOf(bodyContents))
                .build();
    }

    private String[] getRequestLine(BufferedReader reader) throws IOException {
        String firstLine = readLine(reader);
        return split(firstLine, space());
    }

    private String getRequestUri(String[] requestLine) {
        return requestLine[1];
    }

    private String getMethod(String[] requestLine) {
        return requestLine[0];
    }

    private Map<String, String> getHeaderParameters(BufferedReader reader) throws IOException {
        Map<String, String> headerParams = new HashMap<>();

        String line = readLine(reader);
        while (hasContent(line)) {
            String[] mapEntry = split(line, ":");
            System.out.println("Header Parameter Map: " + mapEntry[0].trim() + " => " + mapEntry[1].trim());
            headerParams.put(headerParameterKey(mapEntry), headerParameterValue(mapEntry));

            line = readLine(reader);
        }
        return headerParams;
    }

    private String headerParameterKey(String[] params) {
        return params[0].trim();
    }

    private String headerParameterValue(String[] params) {
        return params[1].trim();
    }

    private char[] getBody(BufferedReader reader, int contentLength) throws IOException {
        char[] bodyContents = new char[contentLength];
        reader.read(bodyContents, 0, contentLength);
        return bodyContents;
    }

    private String readLine(BufferedReader reader) throws IOException {
        return reader.readLine();
    }

    private String[] split(String line, String delimeter) {
        return line.split(delimeter);
    }

    private boolean hasContent(String headerLine) {
        return !headerLine.isEmpty();
    }

    private int getContentLength(Map<String, String> headerParams) {
        String contentLengthFromHeader = headerParams.get("Content-Length");
        return contentLengthFromHeader != null ? Integer.valueOf(contentLengthFromHeader) : 0;
    }

    private String space() {
        return " ";
    }
}
