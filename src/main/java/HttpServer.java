public class HttpServer {
    private final String host;
    private final int port;
    private final HttpServerSocket serverSocket;
    private final RequestParser requestParser;
    private final ResponseBuilder responseBuilder;

    public HttpServer(String host, int port, HttpServerSocket serverSocket, RequestParser requestParser, ResponseBuilder responseBuilder) {
        this.host = host;
        this.port = port;
        this.serverSocket = serverSocket;
        this.requestParser = requestParser;
        this.responseBuilder = responseBuilder;
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
        requestParser.parse(client.getRawHttpRequest());

        //TODO Route and process request

        System.out.println("Creating response now....");
        client.setHttpResponse(responseBuilder.withStatus(404).withReasonPhrase("Not Found").build());
        client.close();
    }
}

