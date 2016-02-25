package server.actions;

import server.*;
import server.messages.HttpRequest;
import server.messages.HttpResponse;
import server.messages.HttpResponseBuilder;

public class UnknownRoute implements Action {
    @Override
    public HttpResponse process(HttpRequest request) {

        return HttpResponseBuilder.anHttpResponseBuilder()
                .withStatusCode(StatusCode.NOT_FOUND)
                .build();
    }
}
