package server.messages;

import server.HttpMethods;
import server.StatusCode;

import java.util.List;


public class HttpResponse {
    private final StatusCode statusCode;
    private String location;
    private final List<HttpMethods> allowedMethods;
    private String httpVersion;
    private boolean authorisationRequest;
    private byte[] body;

    protected HttpResponse(StatusCode statusCode,
                           String httpVersion,
                           String location,
                           List<HttpMethods> allowedMethods,
                           boolean authorisationRequest,
                           byte[] body) {
        this.statusCode = statusCode;
        this.httpVersion = httpVersion;
        this.location = location;
        this.allowedMethods = allowedMethods;
        this.authorisationRequest = authorisationRequest;
        this.body = body;
    }

    public StatusCode statusCode() {
        return statusCode;
    }

    public String httpVersion() {
        return httpVersion;
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

    public boolean authorisationRequest() {
        return authorisationRequest;
    }
}

