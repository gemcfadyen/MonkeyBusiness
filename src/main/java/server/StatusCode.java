package server;

public enum StatusCode {
    OK(200, "OK"),
    NOT_FOUND(404, "Not Found"),
    FOUND(302, "Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    UNAUTHORISED(401, "Unauthorized"),
    PARTIAL_CONTENT(206, "Partial Content");

    private final int statusCode;
    private final String reasonPhrase;

    StatusCode(int statusCode, String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    public int getCode() {
        return statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}
