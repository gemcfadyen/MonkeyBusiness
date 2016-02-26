package server.actions;

import server.Action;
import server.StatusCode;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import static server.messages.HttpResponseBuilder.anHttpResponseBuilder;

public class Authorisation implements Action {
    @Override
    public HttpResponse process(HttpRequest request) {
        System.out.println("Checking Authentication...");
        return anHttpResponseBuilder()
                .withStatusCode(StatusCode.UNAUTHORISED)
                .withAuthorisationRequest()
                .build();
    }
}
