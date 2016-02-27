package server.actions;

import org.junit.Before;
import org.junit.Test;
import server.ResourceHandlerSpy;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static server.HttpMethods.GET;
import static server.StatusCode.PARTIAL_CONTENT;
import static server.messages.HttpRequestBuilder.anHttpRequestBuilder;

public class PartialContentTest {

    private final ResourceHandlerSpy resourceHandlerSpy = new ResourceHandlerSpy();
    private final PartialContent partialContentAction = new PartialContent(resourceHandlerSpy);
    private final Map<String, String> requestedRange = new HashMap<>();


    @Before
    public void setup() {
        requestedRange.put("Range", "bytes=0-4");
    }

    @Test
    public void partialContentRequestContainsStatus206() {
        ResourceHandlerSpy resourceHandlerSpy = new ResourceHandlerSpy();
        PartialContent partialContentAction = new PartialContent(resourceHandlerSpy);
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/partial_content.txt")
                .withHeaderParameters(requestedRange)
                .withRequestLine(GET.name())
                .build();

        HttpResponse httpResponse = partialContentAction.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(PARTIAL_CONTENT));
    }

    @Test
    public void responseContainsTheContentRange() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/partial_content.txt")
                .withRequestLine(GET.name())
                .withHeaderParameters(requestedRange)
                .build();

        HttpResponse httpResponse = partialContentAction.process(httpRequest);

        assertThat(httpResponse.contentRange(), is("bytes=0-4"));
        assertThat(httpResponse.body(), is("My".getBytes()));
        assertThat(resourceHandlerSpy.hasReadPortionOfResource(), is(true));
    }
}
