package server.actions;

import org.junit.Before;
import org.junit.Test;
import server.messages.StatusCode;
import server.messages.HttpRequest;
import server.messages.HttpRequestBuilder;
import server.messages.HttpResponse;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class IncludeParametersInBodyTest {

    private final IncludeParametersInBody includeParametersInBody = new IncludeParametersInBody();
    private final Map<String, String> decodedParams = new HashMap<>();
    private HttpRequest httpRequest;

    @Before
    public void setup() {
        decodedParams.put("key1", "value1");
        decodedParams.put("key2", "value2");
        httpRequest = HttpRequestBuilder.anHttpRequestBuilder()
                .withRequestLine("GET")
                .withRequestUri("/parameters")
                .withParameters(decodedParams)
                .build();
    }

    @Test
    public void responseHasStatus200() {
        HttpResponse response = includeParametersInBody.process(httpRequest);
        assertThat(response.statusCode(), is(StatusCode.OK));
    }

    @Test
    public void responseBodyContainsParameters() {
        HttpResponse response = includeParametersInBody.process(httpRequest);
        assertThat(response.body(), is("key1 = value1,key2 = value2".getBytes()));
    }
}
