package server.actions;

import server.*;
import server.messages.HttpRequest;
import server.messages.HttpResponse;
import server.messages.HttpResponseBuilder;

public class WriteResource implements Action {
    private final ResourceHandler resourceHandler;

    public WriteResource(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    @Override
    public HttpResponse process(HttpRequest request) {
        System.out.println("PUT /FORM");
        resourceHandler.write(request.getRequestUri(), request.getBody());

        return HttpResponseBuilder.anHttpResponseBuilder()
                .withStatusCode(StatusCode.OK)
                .withBody(request.getBody().getBytes())
                .build();
    }
}
