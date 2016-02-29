package server.actions;

import server.Action;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import static server.messages.HttpResponseBuilder.anHttpResponseBuilder;
import static server.messages.StatusCode.FOUND;

public class Redirect implements Action {

    @Override
    public HttpResponse process(HttpRequest request) {

        return anHttpResponseBuilder()
                .withStatusCode(FOUND)
                .withLocation("http://localhost:5000/")
                .build();
    }
}
