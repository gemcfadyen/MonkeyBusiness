package server.actions;

import server.Action;
import server.messages.StatusCode;
import server.messages.HttpRequest;
import server.messages.HttpResponse;
import server.messages.HttpResponseBuilder;

public class Redirect implements Action {

    @Override
    public HttpResponse process(HttpRequest request) {

        return HttpResponseBuilder.anHttpResponseBuilder()
                .withStatusCode(StatusCode.FOUND)
                .withLocation("http://localhost:5000/")
                .build();
    }
}
