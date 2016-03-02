package server.router;

import org.junit.Before;
import org.junit.Test;
import server.Action;
import server.ActionStub;
import server.ResourceHandlerSpy;
import server.messages.HeaderParameterExtractor;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static server.messages.HttpRequestBuilder.anHttpRequestBuilder;
import static server.messages.StatusCode.*;
import static server.router.HttpMethods.GET;

public class HttpRouteProcessorTest {
    private HttpRouteProcessor requestProcessor;

    @Before
    public void setup() {
        Routes routes = new Routes(new ResourceHandlerSpy(), new HeaderParameterExtractor()) {
            public Map<HttpMethods, List<Action>> routes() {
                Map<HttpMethods, List<Action>> routes = new HashMap<>();
                routes.put(GET, asList(new ActionStub(true)));
                return routes;
            }
        };
        requestProcessor = new HttpRouteProcessor(routes);
    }

    @Test
    public void routesBogusMethod() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/anAddress")
                .withRequestLine("BOGUS_METHOD")
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(METHOD_NOT_ALLOWED));
    }

    @Test
    public void configuredRoute() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/aGoodRoute")
                .withRequestLine(GET.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(OK));
    }

    @Test
    public void defaultsTo404WhenNoConfiguredRoutes() {
        requestProcessor = new HttpRouteProcessor(noConfiguredRoutes());

        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/noMatchingRoutes")
                .withRequestLine(GET.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(NOT_FOUND));
    }

    @Test
    public void routesTo404WhenNoEligibleConfiguredRoutes() {
        requestProcessor = new HttpRouteProcessor(noEligibleConfiguredRoutes());

        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/noEligibleMatchingRoutes")
                .withRequestLine(GET.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(NOT_FOUND));

    }

    private Routes noEligibleConfiguredRoutes() {
        return new Routes(new ResourceHandlerSpy(), new HeaderParameterExtractor()) {
            public Map<HttpMethods, List<Action>> routes() {
                Map<HttpMethods, List<Action>> routes = new HashMap<>();
                routes.put(GET, asList(new ActionStub(false)));
                return routes;
            }
        };
    }

    private Routes noConfiguredRoutes() {
        return new Routes(new ResourceHandlerSpy(), new HeaderParameterExtractor()) {
                public Map<HttpMethods, List<Action>> routes() {
                    return new HashMap<>();
                }
            };
    }

}

