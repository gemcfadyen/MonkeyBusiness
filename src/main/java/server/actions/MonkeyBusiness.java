package server.actions;

import server.Action;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import static server.messages.HttpResponseBuilder.anHttpResponseBuilder;
import static server.messages.StatusCode.OK;

public class MonkeyBusiness implements Action {
    @Override
    public boolean isEligible(HttpRequest request) {
        return true;
    }

    @Override
    public HttpResponse process(HttpRequest request) {
        String body = "<html><body><p class=\"qa-monkey\">Mun-key!</p></body></html>";

        return anHttpResponseBuilder()
                .withStatusCode(OK)
                .withBody(body.getBytes())
                .build();
    }
}
