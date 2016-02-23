import java.util.Map;

public class HttpRequestBuilder {

    private String requestLine;
    private String requestUri;
    private Map<String, String> headerParameters;
    private String body;

    public static HttpRequestBuilder anHttpRequestBuilder() {
        return new HttpRequestBuilder();
    }

    public HttpRequestBuilder withRequestLine(String requestLine) {
        this.requestLine = requestLine;
        return this;
    }

    public HttpRequestBuilder withRequestUri(String uri) {
        this.requestUri = uri;
        return this;
    }

    public HttpRequestBuilder withHeaderParameters(Map<String, String> headerParams) {
        this.headerParameters = headerParams;
        return this;
    }

    public HttpRequestBuilder withBody(String body) {
        this.body = body;
        return this;
    }

    public HttpRequest build() {
       return new HttpRequest(requestLine, requestUri, headerParameters, body);
    }
}
