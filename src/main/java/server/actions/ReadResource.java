package server.actions;

import server.Action;
import server.ResourceHandler;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import static server.StatusCode.OK;
import static server.messages.HttpResponseBuilder.anHttpResponseBuilder;

public class ReadResource implements Action {
    private final ResourceHandler resourceHandler;

    public ReadResource(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    @Override
    public HttpResponse process(HttpRequest request) {
        byte[] body = resourceHandler.read(request.getRequestUri());
        return anHttpResponseBuilder()
                .withStatusCode(OK)
                .withBody(body)
                .build();
    }
}
