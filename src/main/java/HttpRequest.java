import java.util.Map;

public class HttpRequest {

    private final String method;
    private String requestUri;
    private Map<String, String> headerParams;
    private String body;

    public HttpRequest(String method,
                       String requestUri,
                       Map<String, String> headerParams,
                       String bodyContent) {
        this.method = method;
        this.requestUri = requestUri;
        this.headerParams = headerParams;
        this.body = bodyContent;
    }

    public String getMethod() {
        return method;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> headerParameters() {
        return headerParams;
    }
}


