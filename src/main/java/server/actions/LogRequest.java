package server.actions;

import server.Action;
import server.ResourceHandler;
import server.StatusCode;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import static server.messages.HttpResponseBuilder.anHttpResponseBuilder;

public class LogRequest implements Action {
    private ResourceHandler resourceHandler;

    public LogRequest(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    @Override
    public HttpResponse process(HttpRequest request) {
        System.out.println("LOG REQUEST~!!!!");
// TODO get the version from the request
        String resourceContent = String.format("%s %s %s\n", request.getMethod(), request.getRequestUri(), "HTTP/1.1");
        resourceHandler.append("/logs", resourceContent);

        return anHttpResponseBuilder()
                .withStatusCode(StatusCode.OK)
                .withBody(resourceContent.getBytes())
                .build();
    }
}