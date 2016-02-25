package server.actions;

import server.*;
import server.messages.HttpRequest;
import server.messages.HttpResponse;
import server.messages.HttpResponseBuilder;

public class MethodOptions implements Action {

    @Override
    public HttpResponse process(HttpRequest request) {
        return HttpResponseBuilder.anHttpResponseBuilder()
                .withStatusCode(StatusCode.OK)
                .withAllowMethods(HttpMethods.values())
                .build();
    }
}
