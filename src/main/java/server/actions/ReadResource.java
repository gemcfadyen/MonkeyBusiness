package server.actions;

import server.Action;
import server.ResourceHandler;
import server.messages.HttpRequest;
import server.messages.HttpResponse;
import server.router.HttpMethods;

import static server.messages.HttpResponseBuilder.anHttpResponseBuilder;
import static server.messages.StatusCode.OK;
import static server.router.Route.LOGS;

public class ReadResource implements Action {
    private final ResourceHandler resourceHandler;

    public ReadResource(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    @Override
    public boolean isEligible(HttpRequest request) {
        return true;
    }

    @Override
    public HttpResponse process(HttpRequest request) {
        logGetRequest(request);

        return anHttpResponseBuilder()
                .withStatusCode(OK)
                .withBody(readResourceAt(request))
                .withAllowMethods(HttpMethods.values())
                .build();
    }

    private byte[] readResourceAt(HttpRequest request) {
        return resourceHandler.read(request.getRequestUri());
    }

    private void logGetRequest(HttpRequest request) {
        String resourceContent = String.format("%s %s %s\n", request.getMethod(), request.getRequestUri(), request.getProtocolVersion());
        resourceHandler.append(LOGS.getPath(), resourceContent);
    }
}
