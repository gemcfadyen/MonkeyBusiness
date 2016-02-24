import java.util.List;


public class HttpResponse {
    private final StatusCode statusCode;
    private String location;
    private final List<HttpMethods> allowedMethods;
    private String httpVersion;
    private String reasonPhrase;
    private byte[] body;

    protected HttpResponse(StatusCode statusCode,
                           String httpVersion,
                           String location,
                           List<HttpMethods> allowedMethods,
                           byte[] body) {
        this.statusCode = statusCode;
        this.httpVersion = httpVersion;
        this.location = location;
        this.reasonPhrase = reasonPhrase;
        this.location = location;
        this.allowedMethods = allowedMethods;
        this.body = body;
    }

    public StatusCode statusCode() {
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

    public byte[] body() {
        return body;
    }

    public String location() {
        return location;
    }
}

