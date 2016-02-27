package server.actions;

import org.junit.Before;
import org.junit.Test;
import server.ResourceHandlerSpy;
import server.messages.HeaderParameterExtractor;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static server.HttpMethods.GET;
import static server.StatusCode.PARTIAL_CONTENT;
import static server.messages.HttpRequestBuilder.anHttpRequestBuilder;

public class PartialContentTest {

    private final ResourceHandlerSpy resourceHandlerSpy = new ResourceHandlerSpy();
    private final PartialContent partialContentAction = new PartialContent(resourceHandlerSpy, new HeaderParameterExtractor());


    @Before
    public void setup() {
    }

    @Test
    public void partialContentRequestContainsStatus206() {
        Map<String, String> headerParams = new HashMap<>();
        headerParams.put("Range", "bytes=0-4");

        ResourceHandlerSpy resourceHandlerSpy = new ResourceHandlerSpy();
        PartialContent partialContentAction = new PartialContent(resourceHandlerSpy, new HeaderParameterExtractor());
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/partial_content.txt")
                .withHeaderParameters(headerParams)
                .withRequestLine(GET.name())
                .build();

        HttpResponse httpResponse = partialContentAction.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(PARTIAL_CONTENT));
    }

    @Test
    public void responseContainsTheContentRange() {
        Map<String, String> headerParams = new HashMap<>();
        headerParams.put("Range", "bytes=0-4");
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/partial_content.txt")
                .withRequestLine(GET.name())
                .withHeaderParameters(headerParams)
                .build();

        HttpResponse httpResponse = partialContentAction.process(httpRequest);

        assertThat(httpResponse.contentRange(), is("bytes=0-4"));
        assertThat(httpResponse.body(), is("My=Da".getBytes()));
        assertThat(resourceHandlerSpy.hasReadResource(), is(true));
    }

    @Test
    public void requestWithNoEndRangeReadsUntilEndOfResource() {
        Map<String, String> headerParams = new HashMap<>();
        headerParams.put("Range", "bytes=4-");
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/partial_content.txt")
                .withRequestLine(GET.name())
                .withHeaderParameters(headerParams)
                .build();

        HttpResponse httpResponse = partialContentAction.process(httpRequest);

        assertThat(httpResponse.contentRange(), is("bytes=4-7"));

        assertThat(httpResponse.body(), is("ata".getBytes()));
        assertThat(resourceHandlerSpy.hasReadResource(), is(true));
    }

    @Test
    public void correctPortionOfResourceReturnedWhenOnlyFirstIndexGiven() {
        Map<String, String> headerParams = new HashMap<>();
        headerParams.put("Range", "bytes=-2");
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/partial_content.txt")
                .withRequestLine(GET.name())
                .withHeaderParameters(headerParams)
                .build();

        HttpResponse httpResponse = partialContentAction.process(httpRequest);

        assertThat(httpResponse.contentRange(), is("bytes=5-7"));

        try {
            System.out.println("Body is " + new String(httpResponse.body(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        assertThat(httpResponse.body(), is("ta".getBytes()));
        assertThat(resourceHandlerSpy.hasReadResource(), is(true));
    }
}
