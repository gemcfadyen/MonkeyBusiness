package server.actions;

import server.Action;
import server.ResourceHandler;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import static server.messages.HttpResponseBuilder.anHttpResponseBuilder;
import static server.messages.StatusCode.OK;
import static server.router.Route.LOGS;

public class WriteResource implements Action {
    private final ResourceHandler resourceHandler;

    public WriteResource(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    @Override
    public boolean isEligible(HttpRequest request) {
        return true;
    }

    @Override
    public HttpResponse process(HttpRequest request) {
        logRequest(request);
        resourceHandler.write(request.getRequestUri(), request.getBody());

        return anHttpResponseBuilder()
                .withStatusCode(OK)
                .withBody(request.getBody().getBytes())
                .build();
    }

    private void logRequest(HttpRequest request) {
        String resourceContent = String.format("%s %s %s\n", request.getMethod(), request.getRequestUri(), request.getProtocolVersion());
        resourceHandler.append(LOGS.getPath(), resourceContent);
    }
}
