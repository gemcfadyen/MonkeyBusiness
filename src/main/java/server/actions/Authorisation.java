package server.actions;

import server.Action;
import server.messages.HeaderParameterExtractor;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import static server.StatusCode.UNAUTHORISED;
import static server.messages.HttpResponseBuilder.anHttpResponseBuilder;

public class Authorisation implements Action {
    private ReadResource readResource;

    public Authorisation(ReadResource readResource) {
        this.readResource = readResource;
    }

    @Override
    public HttpResponse process(HttpRequest request) {
        System.out.println("Checking Authentication...");

        HeaderParameterExtractor headerParameterExtractor = new HeaderParameterExtractor();
        String credentials = headerParameterExtractor.getAuthenticationCredentials(request.headerParameters());

        return isAuthorised(credentials) ?
                readResource.process(request) :
                anHttpResponseBuilder().withStatusCode(UNAUTHORISED)
                        .withAuthorisationRequest()
                        .build();
    }

    private boolean isAuthorised(String credentials) {
        return hasAuthorisationParam(credentials)
                && isCorrect(credentials);
    }

    private boolean hasAuthorisationParam(String authorisationParam) {
        return !(authorisationParam == null);
    }

    private boolean isCorrect(String credentials) {
        String[] encrypted = credentials.split(space());
        for (String encryptedParam : encrypted) {
            if (isCredentials(encryptedParam)
                    && authenticated(encryptedParam)) {
                return true;
            }
        }

        return false;
    }

    private String space() {
        return " ";
    }

    private boolean isCredentials(String encryptedParam) {
        return !encryptedParam.equals("Basic");
    }

    private boolean authenticated(String encryptedParam) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedParam.trim());
            return decode(decodedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    protected boolean decode(byte[] decodedBytes) throws UnsupportedEncodingException {
        return new String(decodedBytes, "UTF-8").equals("admin:hunter2");
    }
}