package server.actions;

import server.Action;
import server.router.HttpMethods;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import static server.messages.StatusCode.OK;
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
