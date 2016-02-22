import java.io.UnsupportedEncodingException;

class HttpResponse {
    private final int statusCode;
    private String httpVersion;
    private String reasonPhrase;

    public HttpResponse(int statusCode, String httpVersion, String reasonPhrase) {
        this.statusCode = statusCode;
        this.httpVersion = httpVersion;
        this.reasonPhrase = reasonPhrase;
    }

    public byte[] formatForClient() {
        try {
            return formatResponse();
        } catch (UnsupportedEncodingException e) {
            throw new HttpResponseException("Error in creating HTTP Response", e);
        }
    }

    protected byte[] formatResponse() throws UnsupportedEncodingException {
        String formattedResponse = statusLine();
        System.out.println("Formatted response " + formattedResponse);
        return formattedResponse.getBytes("UTF-8");
    }

    private String statusLine() {
        return httpVersion + space() + statusCode + space() + reasonPhrase + endOfStatusLine();
    }

    private String endOfStatusLine() {
        return "\r\n\r\n";
    }

    private String space() {
        return " ";
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
}

