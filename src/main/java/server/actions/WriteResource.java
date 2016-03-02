package server.actions;

import server.Action;
import server.ResourceHandler;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import static server.messages.HttpResponseBuilder.anHttpResponseBuilder;
import static server.messages.StatusCode.OK;

public class WriteResource extends LogRequest implements Action {

    public WriteResource(ResourceHandler resourceHandler) {
        super(resourceHandler);
    }

    @Override
    public boolean isEligible(HttpRequest request) {
        return true;
    }

    @Override
    public HttpResponse createHttpResponse(HttpRequest request) {
        resourceHandler.write(request.getRequestUri(), request.getBody());

        return anHttpResponseBuilder()
                .withStatusCode(OK)
                .withBody(request.getBody().getBytes())
                .build();
    }
}
