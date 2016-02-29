package server.actions;

import server.Action;
import server.ResourceHandler;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import static server.Route.LOGS;
import static server.StatusCode.OK;
import static server.messages.HttpResponseBuilder.anHttpResponseBuilder;

public class LogRequest implements Action {
    private ResourceHandler resourceHandler;

    public LogRequest(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    @Override
    public HttpResponse process(HttpRequest request) {
        String resourceContent = String.format("%s %s %s\n", request.getMethod(), request.getRequestUri(), request.getProtocolVersion());
        resourceHandler.append(LOGS.getPath(), resourceContent);

        return anHttpResponseBuilder()
                .withStatusCode(OK)
                .build();
    }
}