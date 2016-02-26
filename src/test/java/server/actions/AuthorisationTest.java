package server.actions;

import org.junit.Test;
import server.StatusCode;
import server.messages.HttpRequest;
import server.messages.HttpRequestBuilder;
import server.messages.HttpResponse;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static server.HttpMethods.GET;

public class AuthorisationTest {

    @Test
    public void returns403WhenRequestDoesNoWWtContainAuthorisationFields() {
        HttpRequest httpRequest = HttpRequestBuilder.anHttpRequestBuilder()
                .withRequestUri("/logs")
                .withRequestLine(GET.name())
                .build();

        Authorisation authorisation = new Authorisation();
        HttpResponse httpResponse = authorisation.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.UNAUTHORISED));
    }

    @Test
    public void returnsResponseAskingForAuthentication() {
        HttpRequest httpRequest = HttpRequestBuilder.anHttpRequestBuilder()
                .withRequestUri("/logs")
                .withRequestLine(GET.name())
                .build();

        Authorisation authorisation = new Authorisation();
        HttpResponse httpResponse = authorisation.process(httpRequest);

        assertThat(httpResponse.authorisationRequest(), is(true));
    }
}
