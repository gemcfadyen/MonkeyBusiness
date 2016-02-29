package server.actions;

import server.Action;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import static server.messages.HttpResponseBuilder.anHttpResponseBuilder;
import static server.messages.StatusCode.NOT_FOUND;

public class UnknownRoute implements Action {
    @Override
    public HttpResponse process(HttpRequest request) {

        return anHttpResponseBuilder()
                .withStatusCode(NOT_FOUND)
                .build();
    }
}
