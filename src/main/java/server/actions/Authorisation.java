package server.actions;

import com.google.common.base.Strings;
import server.Action;
import server.messages.HttpRequest;
import server.messages.HttpResponse;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.util.Map;

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

        Map<String, String> headerParams = request.headerParameters();
        String credentials = headerParams.get("Authorization");

        return authorised(credentials) ?
                readResource.process(request) :
                anHttpResponseBuilder().withStatusCode(UNAUTHORISED)
                        .withAuthorisationRequest()
                        .build();
    }

    private boolean authorised(String credentials) {
        return hasAuthorisation(credentials) && isCorrect(credentials);
    }

    private boolean hasAuthorisation(String authorisationParam) {
        return !Strings.isNullOrEmpty(authorisationParam);
    }

    private boolean isCorrect(String credentials) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] decodedBytes = decoder.decodeBuffer(credentials);
            if (authenticated(new String(decodedBytes, "UTF-8"))) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    protected boolean authenticated(String decryptedCredentials) {
        return decryptedCredentials.equals("admin:hunter2");
    }
}
