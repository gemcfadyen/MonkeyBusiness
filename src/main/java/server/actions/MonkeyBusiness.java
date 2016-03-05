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
        String body = "<html><body>" + body() + "</body></html>";

        return anHttpResponseBuilder()
                .withStatusCode(OK)
                .withBody(body.getBytes())
                .build();
    }

    private String body() {
        StringBuilder sb = new StringBuilder();
        sb.append("<table class=\"cage\">");
        for (int i = 0; i < 10; i++) {
            sb.append("<tr>");
            for (int j = 0; j < 10; j++) {
                sb.append("<td class=\"bed empty-bed\">");
                sb.append("</td>");

            }
            sb.append("</tr>");
        }

        sb.append("</table>");
        return sb.toString();
    }
}
