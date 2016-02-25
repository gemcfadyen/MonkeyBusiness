import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class HttpResponseBuilder {
    private StatusCode statusCode;
    private List<HttpMethods> allowedMethods = new ArrayList<>();
    private byte[] body;
    private String location;

    public static HttpResponseBuilder anHttpResponseBuilder() {
        return new HttpResponseBuilder();
    }

    public HttpResponse build() {
        return new HttpResponse(statusCode, "HTTP/1.1", location, allowedMethods, body);
    }

    public HttpResponseBuilder withStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public HttpResponseBuilder withAllowMethods(HttpMethods... supportedMethods) {
        Collections.addAll(allowedMethods, supportedMethods);
        return this;
    }

    public HttpResponseBuilder withLocation(String redirectUrl) {
        this.location = redirectUrl;
        return this;
    }

    public HttpResponseBuilder withBody(byte[] content) {
        this.body = content;
        return this;
    }
}
