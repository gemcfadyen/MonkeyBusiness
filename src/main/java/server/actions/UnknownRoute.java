package server.actions;

import server.Action;
import server.messages.HttpRequest;
import server.messages.HttpResponse;
import server.messages.HttpResponseBuilder;

import static server.StatusCode.NOT_FOUND;

public class UnknownRoute implements Action {
    @Override
    public HttpResponse process(HttpRequest request) {

        return HttpResponseBuilder.anHttpResponseBuilder()
                .withStatusCode(NOT_FOUND)
                .build();
    }
}
