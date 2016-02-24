import java.util.List;


class HttpResponse {
    private final int statusCode;
    private final List<HttpMethods> allowedMethods;
    private String httpVersion;
    private String reasonPhrase;

    protected HttpResponse(int statusCode,
                           String httpVersion,
                           String reasonPhrase,
                           List<HttpMethods> allowedMethods) {
        this.statusCode = statusCode;
        this.httpVersion = httpVersion;
        this.reasonPhrase = reasonPhrase;
        this.allowedMethods = allowedMethods;
    }

    public int statusCode() {
        return statusCode;
    }

    public String httpVersion() {
        return httpVersion;
    }

    public String reasonPhrase() {
        return reasonPhrase;
    }

    public List<HttpMethods> allowedMethods() {
        return allowedMethods;
    }
}

