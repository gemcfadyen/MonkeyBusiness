import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class HttpServer {
    private String host;
    private int port;
    private HttpServerSocket serverSocket;
    private HttpRequestParser httpRequestParser;

    public HttpServer(String host, int port, HttpServerSocket serverSocket, HttpRequestParser httpRequestParser) {
        this.host = host;
        this.port = port;
        this.serverSocket = serverSocket;
        this.httpRequestParser = httpRequestParser;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void start() {
        HttpSocket client = serverSocket.accept();
        HttpRequest httpRequest = httpRequestParser.parse(client.getRawHttpRequest());
    }
}

interface HttpServerSocket {
    HttpSocket accept();
}

class ServerSocketSpy implements HttpServerSocket {
    private boolean isAcceptingRequests = false;

    @Override
    public HttpSocket accept() {
        isAcceptingRequests = true;
        return new FakeClient();
    }

    public boolean isAcceptingRequests() {
        return isAcceptingRequests;
    }
}

class FakeClient implements HttpSocket {

    @Override
    public InputStream getRawHttpRequest() {
        return new ByteArrayInputStream("An Http Request".getBytes());
    }
}

interface HttpSocket {
    InputStream getRawHttpRequest();
}

interface HttpRequestParser {

    HttpRequest parse(InputStream inputStream);

}

class HttpRequestParserSpy implements HttpRequestParser {
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

class HttpRequest {

}