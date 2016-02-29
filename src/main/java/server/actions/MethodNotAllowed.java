package server.actions;

import server.Action;
import server.router.HttpMethods;
import server.messages.StatusCode;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import static server.messages.HttpResponseBuilder.anHttpResponseBuilder;

public class MethodNotAllowed implements Action {

    @Override
    public HttpResponse process(HttpRequest request) {

        return anHttpResponseBuilder()
                .withStatusCode(StatusCode.METHOD_NOT_ALLOWED)
                .withAllowMethods(HttpMethods.values())
                .build();
    }
}
