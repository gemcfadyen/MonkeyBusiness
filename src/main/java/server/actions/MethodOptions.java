package server.actions;

import server.Action;
import server.HttpMethods;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import static server.StatusCode.OK;
import static server.messages.HttpResponseBuilder.anHttpResponseBuilder;

public class MethodOptions implements Action {

    @Override
    public HttpResponse process(HttpRequest request) {
        return anHttpResponseBuilder()
                .withStatusCode(OK)
                .withAllowMethods(HttpMethods.values())
                .build();
    }
}
