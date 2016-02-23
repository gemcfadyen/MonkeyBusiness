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
        String[] requestLine;

        try {
            requestLine = getRequestLine(reader);
            Map<String, String> headerParams = getHeaderParameters(reader);
            char[] bodyContents = getBody(reader, getContentLength(headerParams));

            //TODO maybe should be a builder aswell
            return new HttpRequest(getMethod(requestLine), getRequestUri(requestLine), headerParams, String.valueOf(bodyContents));
        } catch (IOException e) {
            throw new HttpRequestParsingException("Error in parsing Http Request", e);
        }
    }

    private char[] getBody(BufferedReader reader, int contentLength) throws IOException {
        char[] bodyContents = new char[contentLength];
        reader.read(bodyContents, 0, contentLength);
        System.out.println("body is: " + String.valueOf(bodyContents));
        return bodyContents;
    }

    private int getContentLength(Map<String, String> headerParams) {
        String contentLengthFromHeader = headerParams.get("Content-Length");
        return contentLengthFromHeader != null ? Integer.valueOf(contentLengthFromHeader) : 0;
    }

    private String[] getRequestLine(BufferedReader reader) throws IOException {
        String[] requestLine;
        String firstLine = readLine(reader);
        requestLine = parseRequestLine(firstLine);
        return requestLine;
    }

    private Map<String, String> getHeaderParameters(BufferedReader reader) throws IOException {
        Map<String, String> headerParams = new HashMap<>();

        String headerLine = readLine(reader);
        while (!headerLine.isEmpty()) {
            String[] mapEntry = headerLine.split(":");
            System.out.println("make into a map" + mapEntry[0].trim() + " => " + mapEntry[1].trim());
            headerParams.put(mapEntry[0].trim(), mapEntry[1].trim());
            headerLine = readLine(reader);
        }

//        if (headerParams.get("Content-Length") == null) {
//            headerParams.put("Content-Length", "0");
//        }

        return headerParams;
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
