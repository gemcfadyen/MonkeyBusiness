package server.actions;

import org.junit.Test;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static server.messages.HttpRequestBuilder.anHttpRequestBuilder;
import static server.messages.StatusCode.NOT_FOUND;
import static server.router.HttpMethods.GET;

public class UnknownRouteTest {
    private UnknownRoute unknownRoute = new UnknownRoute();

    @Test
    public void isEligibleForRouteFooBar() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/foobar")
                .withRequestLine(GET.name())
                .build();

        assertThat(unknownRoute.isEligible(httpRequest), is(true));
    }

    @Test
    public void isNotEligibleForAnotherRoute() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/logs")
                .withRequestLine(GET.name())
                .build();

        assertThat(unknownRoute.isEligible(httpRequest), is(false));
    }

    @Test
    public void provides404WhenNoRoutesMet() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/unknown/route")
                .withRequestLine(GET.name())
                .build();

        HttpResponse httpResponse = unknownRoute.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(NOT_FOUND));
    }
}
