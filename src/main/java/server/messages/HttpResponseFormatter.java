package server.messages;

import server.DelimitedFormatter;
import server.HttpMethods;
import server.ResponseFormatter;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import static server.Delimiter.COMMA;

public class HttpResponseFormatter implements ResponseFormatter {

    private DelimitedFormatter listFormatter = new DelimitedFormatter<HttpMethods>();

    @Override
    public byte[] format(HttpResponse response) {
        try {
            return createFormatted(response);
        } catch (UnsupportedEncodingException e) {
            throw new HttpResponseException("Error in creating HTTP Response", e);
        }
    }

    protected byte[] createFormatted(HttpResponse response) throws UnsupportedEncodingException {
        String formattedHeader = createHeader(response);
        System.out.println("formatted header is  " + formattedHeader + "\n--------------\n");
        byte[] formattedBody = createBody(response);
        return createWholeResponse(formattedHeader.getBytes("UTF-8"), formattedBody);
    }

    private byte[] createWholeResponse(byte[] headerBytes, byte[] bodyBytes) {
        if (hasBody(bodyBytes)) {
            ByteBuffer entireResponse = ByteBuffer.allocate(headerBytes.length + bodyBytes.length);
            entireResponse.put(headerBytes);
            entireResponse.put(bodyBytes);
            return entireResponse.array();
        } else {
            return headerBytes;
        }
    }

    private boolean hasBody(byte[] bodyBytes) {
        return bodyBytes != null;
    }

    private byte[] createBody(HttpResponse response) {
        return response.body();
    }

    private String createHeader(HttpResponse response) {
        String formattedHeader = formatStatusLine(response);
        if (hasAllowMethods(response)) {
            formattedHeader += addAllowLineToHeader(response);
        }

        if (hasLocation(response)) {
            formattedHeader += addLocationToHeader(response);
        }

        if (response.authorisationRequest()) {
            formattedHeader += addAuthorisationRequestToHeader();
        }

        formattedHeader += endOfHeader();
        return formattedHeader;
    }

    private String addAllowLineToHeader(HttpResponse response) {
        return endOfLine() + "Allow: " + listFormatter.delimitedValues(response.allowedMethods(), COMMA);
    }

    private boolean hasLocation(HttpResponse response) {
        String location = response.location();
        return !(location == null || location.equals(""));
    }

    private String addLocationToHeader(HttpResponse response) {
        return endOfLine() + "Location: " + response.location();
    }

    private boolean hasAllowMethods(HttpResponse response) {
        return response.allowedMethods().size() > 0;
    }

    private String addAuthorisationRequestToHeader() {
        return endOfLine() + "WWW-Authenticate: Basic realm=\"My Server\"";
    }

    private String formatStatusLine(HttpResponse response) {
        return response.httpVersion() + space()
                + response.statusCode().getCode() + space()
                + response.statusCode().getReasonPhrase();
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
