package server.actions;

import server.*;
import server.messages.HttpRequest;
import server.messages.HttpResponse;
import server.messages.HttpResponseBuilder;

public class ReadResource implements Action {
    private final ResourceHandler resourceHandler;

    public ReadResource(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    @Override
    public HttpResponse process(HttpRequest request) {
        byte[] body = resourceHandler.read(request.getRequestUri());
        return HttpResponseBuilder.anHttpResponseBuilder()
                .withStatusCode(StatusCode.OK)
                .withBody(body)
                .build();
    }
}
