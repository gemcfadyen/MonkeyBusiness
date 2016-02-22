import java.util.Date;

public class HttpResponseBuilder implements ResponseBuilder {
    private String httpVersion = "HTTP/1.1";
    private int statusCode;
    private String reasonPhrase;

    public static HttpResponseBuilder anHttpResponseBuilder() {
        return new HttpResponseBuilder();
    }

    @Override
    public HttpResponse build() {

        Date today = new Date();
        System.out.println("Returning a canned response!");
        String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;

        return new HttpResponse(statusCode, httpVersion, reasonPhrase);
    }

    public ResponseBuilder withStatus(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public ResponseBuilder withReasonPhrase(String phrase) {
        this.reasonPhrase = phrase;
        return this;
    }
}
