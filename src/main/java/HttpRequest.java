public class HttpRequest {

    private final String method;
    private String requestUri;

    public HttpRequest(String method, String requestUri) {
        this.method = method;
        this.requestUri = requestUri;
    }

    public String getMethod() {
        return method;
    }

    public String getRequestUri() {
        return requestUri;
    }
}
