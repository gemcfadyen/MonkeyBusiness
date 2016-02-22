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

