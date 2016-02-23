import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HttpResponseBuilder implements ResponseBuilder {
    private String httpVersion = "HTTP/1.1";
    private int statusCode;
    private String reasonPhrase;
    private List<String> allowedMethods = new ArrayList<>();

    public static HttpResponseBuilder anHttpResponseBuilder() {
        return new HttpResponseBuilder();
    }

    @Override
    public HttpResponse build() {
        return new HttpResponse(statusCode, httpVersion, reasonPhrase, allowedMethods);
    }

    @Override
    public ResponseBuilder withStatus(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    @Override
    public ResponseBuilder withReasonPhrase(String phrase) {
        this.reasonPhrase = phrase;
        return this;
    }

    @Override
    public ResponseBuilder withAllowMethods(String... supportedMethods) {
        Collections.addAll(allowedMethods, supportedMethods);
        return this;
    }
}
