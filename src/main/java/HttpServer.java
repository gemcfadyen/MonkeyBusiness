public class HttpServer {
    private final String host;
    private final int port;
    private final HttpServerSocket serverSocket;
    private final RequestParser requestParser;
    private RequestProcessor httpRequestProcessor;

    public HttpServer(String host, int port,
                      HttpServerSocket serverSocket,
                      RequestParser requestParser,
                      RequestProcessor httpRequestProcessor) {
        this.host = host;
        this.port = port;
        this.serverSocket = serverSocket;
        this.requestParser = requestParser;
        this.httpRequestProcessor = httpRequestProcessor;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void processRequest() {
        System.out.println("Listening for request.....");

        HttpSocket client = serverSocket.accept();
        HttpRequest httpRequest = requestParser.parse(client.getRawHttpRequest());

        HttpResponse httpResponse = httpRequestProcessor.process(httpRequest);

        client.setHttpResponse(httpResponse);
        client.close();
    }
}

