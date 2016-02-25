package server.actions;

import org.junit.Test;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static server.HttpMethods.GET;
import static server.StatusCode.NOT_FOUND;
import static server.messages.HttpRequestBuilder.anHttpRequestBuilder;

public class UnknownRouteTest {
    private UnknownRoute unknownRoute = new UnknownRoute();

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
