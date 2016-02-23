import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class HttpResponseBuilder {
    private int statusCode;
    private String reasonPhrase;
    private List<HttpMethods> allowedMethods = new ArrayList<>();

    public static HttpResponseBuilder anHttpResponseBuilder() {
        return new HttpResponseBuilder();
    }

    public HttpResponse build() {
        return new HttpResponse(statusCode, "HTTP/1.1", reasonPhrase, allowedMethods);
    }

    public HttpResponseBuilder withStatus(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public HttpResponseBuilder withReasonPhrase(String phrase) {
        this.reasonPhrase = phrase;
        return this;
    }

    public HttpResponseBuilder withAllowMethods(HttpMethods... supportedMethods) {
        Collections.addAll(allowedMethods, supportedMethods);
        return this;
    }
}
