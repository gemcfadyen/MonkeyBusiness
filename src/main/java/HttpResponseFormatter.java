import java.io.UnsupportedEncodingException;
import java.util.List;

public class HttpResponseFormatter implements ResponseFormatter {
    @Override
    public byte[] format(HttpResponse response) {
        try {
            return createFormatted(response);
        } catch (UnsupportedEncodingException e) {
            throw new HttpResponseException("Error in creating HTTP Response", e);
        }
    }

    protected byte[] createFormatted(HttpResponse response) throws UnsupportedEncodingException {
        String formattedHeader = formatStatusLine(response);
        if (hasAllowMethods(response)) {
            formattedHeader += addAllowLineToHeader(response);
        }
        formattedHeader += endOfHeader();

        System.out.println("formatted response is  " + formattedHeader);
        return formattedHeader.getBytes("UTF-8");
    }

    private String addAllowLineToHeader(HttpResponse response)  {
        return endOfLine() + commaDelimitAllowParameters(response);
    }

    private boolean hasAllowMethods(HttpResponse response) {
        return response.allowedMethods().size() > 0;
    }

    private String commaDelimitAllowParameters(HttpResponse response) { //String formattedResponse) {
        List<String> allowMethods = response.allowedMethods();
        String allowedLine = "";
        String firstMethod = "";
        if (allowMethods.size() > 0) {
            firstMethod = allowMethods.get(0);
            for (int i = 1; i < allowMethods.size(); i++) {
                allowedLine += "," + allowMethods.get(i);
            }
            return "Allow: " + firstMethod + allowedLine;
        }
        return "";
    }

    private String formatStatusLine(HttpResponse response) {
        return response.httpVersion() + space()
                + response.statusCode() + space()
                + response.reasonPhrase();
    }

    private String endOfHeader() {
        return endOfLine() + endOfLine();
    }

    private String endOfLine() {
        return "\r\n";
    }

    private String space() {
        return " ";
    }
}
