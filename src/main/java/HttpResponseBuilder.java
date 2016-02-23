import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class HttpResponseBuilder {
    private String httpVersion = "HTTP/1.1";
    private int statusCode;
    private String reasonPhrase;
    private List<String> allowedMethods = new ArrayList<>();

    public static HttpResponseBuilder anHttpResponseBuilder() {
        return new HttpResponseBuilder();
    }

    public HttpResponse build() {
        return new HttpResponse(statusCode, httpVersion, reasonPhrase, allowedMethods);
    }

    public HttpResponseBuilder withStatus(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public HttpResponseBuilder withReasonPhrase(String phrase) {
        this.reasonPhrase = phrase;
        return this;
    }

    public HttpResponseBuilder withAllowMethods(String... supportedMethods) {
        Collections.addAll(allowedMethods, supportedMethods);
        return this;
    }
}
