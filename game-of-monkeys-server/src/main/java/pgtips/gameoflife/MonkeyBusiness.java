package pgtips.gameoflife;

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
        String style = style();
        String body = "<html>" + style + "<body>" + body() + "</body></html>";

        return anHttpResponseBuilder()
                .withStatusCode(OK)
                .withBody(body.getBytes())
                .build();
    }

    private String style() {
        StringBuilder sb = new StringBuilder();
        sb.append("<style>");
        sb.append("body { background:\n" +
                "radial-gradient(circle farthest-side at 0% 50%,#fb1 23.5%,rgba(240,166,17,0) 0)21px 30px,\n" +
                "radial-gradient(circle farthest-side at 0% 50%,#B71 24%,rgba(240,166,17,0) 0)19px 30px,\n" +
                "linear-gradient(#fb1 14%,rgba(240,166,17,0) 0, rgba(240,166,17,0) 85%,#fb1 0)0 0,\n" +
                "linear-gradient(150deg,#fb1 24%,#B71 0,#B71 26%,rgba(240,166,17,0) 0,rgba(240,166,17,0) 74%,#B71 0,#B71 76%,#fb1 0)0 0,\n" +
                "linear-gradient(30deg,#fb1 24%,#B71 0,#B71 26%,rgba(240,166,17,0) 0,rgba(240,166,17,0) 74%,#B71 0,#B71 76%,#fb1 0)0 0,\n" +
                "linear-gradient(90deg,#B71 2%,#fb1 0,#fb1 98%,#B71 0%)0 0 #fb1;\n" +
                "background-size:40px 60px; }");

        sb.append(".cage { margin: auto; border: 40px solid black; }");
        sb.append(".bed { border: 10px solid black;" +
                "height: 60px;" +
                "width: 60px; }");
        sb.append(".empty-bed { background-color: #FFA500;}");
        sb.append(".monkey-in-bed { background-image: url('http://vignette4.wikia.nocookie.net/survivor-org/images/9/9b/Jumping-monkey-gif-animation_(1).gif');" +
                "background-repeat: no-repeat;" +
                "background-position: center;" +
                "background-size:contain;  }");
        sb.append("</style>");
        return sb.toString();
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
