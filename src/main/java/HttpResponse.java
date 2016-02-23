import java.io.UnsupportedEncodingException;
import java.util.List;

class HttpResponse {
    private final int statusCode;
    private final List<String> allowedMethods;
    private String httpVersion;
    private String reasonPhrase;

    public HttpResponse(int statusCode, String httpVersion, String reasonPhrase, List<String> allowedMethods) {
        this.statusCode = statusCode;
        this.httpVersion = httpVersion;
        this.reasonPhrase = reasonPhrase;
        this.allowedMethods = allowedMethods;
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

        if (allowedMethods.size() > 0) {
            String allowedLine = "";
            String firstMethod = allowedMethods.get(0);
            for (int i = 1; i < allowedMethods().size(); i++) {
                allowedLine += "," + allowedMethods.get(i);
            }


            formattedResponse = formattedResponse + "\r\nAllow: " + firstMethod + allowedLine;
        }
        System.out.println("formatted response is  " + formattedResponse);
        return (formattedResponse + endOfStatusLine()).getBytes("UTF-8");
    }

    private String statusLine() {
        return httpVersion + space() + statusCode + space() + reasonPhrase;
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

    public List<String> allowedMethods() {
        return allowedMethods;
    }
}

