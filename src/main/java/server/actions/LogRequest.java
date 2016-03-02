package server.actions;

import server.Action;
import server.ResourceHandler;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import static server.messages.HttpResponseBuilder.anHttpResponseBuilder;
import static server.messages.StatusCode.OK;
import static server.router.Route.LOGS;

public class LogRequest implements Action {
    private ResourceHandler resourceHandler;

    public LogRequest(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    @Override
    public boolean isEligible(HttpRequest request) {
        return false;
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