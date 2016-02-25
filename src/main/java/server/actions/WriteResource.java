package server.actions;

import server.Action;
import server.ResourceHandler;
import server.StatusCode;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import static server.messages.HttpResponseBuilder.anHttpResponseBuilder;

public class WriteResource implements Action {
    private final ResourceHandler resourceHandler;

    public WriteResource(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    @Override
    public HttpResponse process(HttpRequest request) {
        resourceHandler.write(request.getRequestUri(), request.getBody());

        return anHttpResponseBuilder()
                .withStatusCode(StatusCode.OK)
                .withBody(request.getBody().getBytes())
                .build();
    }
}
