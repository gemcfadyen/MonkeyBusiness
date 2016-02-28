package server;

import server.messages.HttpRequest;

public class ExecutorTask implements Runnable {
    private final HttpSocket client;
    private final RequestParser requestParser;
    private final RouteProcessor routeProcessor;

    public ExecutorTask(HttpSocket client, RequestParser requestParser, RouteProcessor routeProcessor) {
        this.client = client;
        this.requestParser = requestParser;
        this.routeProcessor = routeProcessor;
    }

    @Override
    public void run() {
        HttpRequest httpRequest = requestParser.parse(client.getRawHttpRequest());

        client.setHttpResponse(routeProcessor.process(httpRequest));
        client.close();
    }
}
