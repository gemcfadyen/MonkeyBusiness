import java.util.List;


class HttpResponse {
    private final int statusCode;
    private final List<String> allowedMethods;
    private String httpVersion;
    private String reasonPhrase;

    protected HttpResponse(int statusCode,
                           String httpVersion,
                           String reasonPhrase,
                           List<String> allowedMethods) {
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

    public List<String> allowedMethods() {
        return allowedMethods;
    }
}

