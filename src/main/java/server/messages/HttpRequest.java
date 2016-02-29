package server.messages;

import java.util.Map;

public class HttpRequest {

    private final String method;
    private final Map<String, String> requestParams;
    private final String requestUri;
    private final Map<String, String> headerParams;
    private final String body;

    protected HttpRequest(String method,
                          String requestUri,
                          Map<String, String> requestParams,
                          Map<String, String> headerParams,
                          String bodyContent) {
        this.method = method;
        this.requestUri = requestUri;
        this.requestParams = requestParams;
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

    public Map<String, String> params() {
        return requestParams;
    }

    public String getProtocolVersion() {
        return "HTTP/1.1";
    }
}


