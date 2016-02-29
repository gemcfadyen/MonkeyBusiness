package server.router;

import org.junit.Before;
import org.junit.Test;
import server.ResourceHandlerSpy;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static server.messages.HttpMessageHeaderProperties.PARTIAL_CONTENT_RANGE;
import static server.messages.HttpRequestBuilder.anHttpRequestBuilder;
import static server.messages.StatusCode.*;
import static server.router.HttpMethods.*;

public class HttpRouteProcessorTest {
    private HttpRouteProcessor requestProcessor;
    private ResourceHandlerSpy resourceHandlerSpy;

    @Before
    public void setup() {
        resourceHandlerSpy = new ResourceHandlerSpy();
        requestProcessor = new HttpRouteProcessor(resourceHandlerSpy);
    }

    @Test
    public void routesNotConfiguredResultIn404Response() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/unknown/route")
                .withRequestLine(GET.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(NOT_FOUND));
    }

    @Test
    public void routesHomeToDirectoryListing() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/")
                .withRequestLine(GET.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(OK));
        assertThat(resourceHandlerSpy.hasGotDirectoryContent(), is(true));
    }

    @Test
    public void routesFormAndGetToReadResource() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/form")
                .withRequestLine(GET.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(OK));
        assertThat(resourceHandlerSpy.hasReadResource(), is(true));
    }

    @Test
    public void routesFormAndPostToWriteResource() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/form")
                .withBody("content")
                .withRequestLine(POST.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(OK));
        assertThat(httpResponse.body(), is("content".getBytes()));
        assertThat(resourceHandlerSpy.hasWrittenToResource(), is(true));
    }

    @Test
    public void routesFormAndPutToWriteResource() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/form")
                .withRequestLine(PUT.name())
                .withBody("content")
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(OK));
        assertThat(httpResponse.body(), is("content".getBytes()));
        assertThat(resourceHandlerSpy.hasWrittenToResource(), is(true));
    }

    @Test
    public void routesFormAndDeleteToRemoveResource() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/form")
                .withRequestLine(DELETE.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(OK));
        assertThat(resourceHandlerSpy.hasDeletedResource(), is(true));
    }

    @Test
    public void routesMethodOptionsReturningAllowHeaders() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/method_options")
                .withRequestLine(OPTIONS.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(OK));
        assertThat(httpResponse.allowedMethods(), containsInAnyOrder(GET, HEAD, POST, OPTIONS, PUT, DELETE, PATCH));
    }

    @Test
    public void routesRedirecToNewUrl() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/redirect")
                .withRequestLine(GET.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(FOUND));
        assertThat(httpResponse.location(), is("http://localhost:5000/"));
    }

    @Test
    public void routesJpegImageToReadResource() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/image.jpeg")
                .withRequestLine(GET.name())
                .withBody("My=Data")
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(OK));
        assertThat(httpResponse.body(), is("My=Data".getBytes()));
        assertThat(resourceHandlerSpy.hasReadResource(), is(true));
    }

    @Test
    public void routesPngImageToReadResource() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/image.png")
                .withRequestLine(GET.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(OK));
        assertThat(httpResponse.body(), is("My=Data".getBytes()));
        assertThat(resourceHandlerSpy.hasReadResource(), is(true));
    }

    @Test
    public void routesGifImageToReadResource() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/image.gif")
                .withRequestLine(GET.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(OK));
        assertThat(httpResponse.body(), is("My=Data".getBytes()));
        assertThat(resourceHandlerSpy.hasReadResource(), is(true));
    }

    @Test
    public void routesFile1ToReadResource() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/file1")
                .withRequestLine(GET.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(OK));
        assertThat(httpResponse.body(), is("My=Data".getBytes()));
        assertThat(resourceHandlerSpy.hasReadResource(), is(true));
    }

    @Test
    public void routesFileOneAndPutToMethodNotAllowed() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/file1")
                .withRequestLine(PUT.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(METHOD_NOT_ALLOWED));
    }

    @Test
    public void routesUnknownHttpMethodToMethodNotAllowed() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/file1")
                .withRequestLine("BOGUS_METHOD")
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(METHOD_NOT_ALLOWED));
    }

    @Test
    public void routesTextFileToReadRequest() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/text-file.txt")
                .withRequestLine(GET.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(OK));
        assertThat(resourceHandlerSpy.hasReadResource(), is(true));
    }

    @Test
    public void routesTextFilePostToMethodNotAllowed() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/text-file.txt")
                .withRequestLine(POST.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(METHOD_NOT_ALLOWED));
    }

    @Test
    public void routesParameters() {
        Map<String, String> decodedParams = new HashMap<>();
        decodedParams.put("paramKey", "paramValue");
        decodedParams.put("anotherParamKey", "anotherParamValue");
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/parameters")
                .withRequestLine(GET.name())
                .withParameters(decodedParams)
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(OK));
    }

    @Test
    public void routesToLogsWithNoAuthenticationReturnsUnauthorised() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/logs")
                .withRequestLine(GET.name())
                .withHeaderParameters(new HashMap<>())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(UNAUTHORISED));
    }

    @Test
    public void getLogWritesToLog() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/log")
                .withRequestLine(GET.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(OK));
        assertThat(resourceHandlerSpy.hasAppendedToResource(), is(true));
    }

    @Test
    public void putTheseWritesToLog() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/these")
                .withRequestLine(PUT.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(OK));
        assertThat(resourceHandlerSpy.hasAppendedToResource(), is(true));
    }

    @Test
    public void headRequestsWritesToLog() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/requests")
                .withRequestLine(HEAD.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(OK));
        assertThat(httpResponse.body(), nullValue());
        assertThat(resourceHandlerSpy.hasAppendedToResource(), is(true));
    }

    @Test
    public void routesPartialContent() {
        Map<String, String> headerParams = new HashMap<>();
        headerParams.put(PARTIAL_CONTENT_RANGE.getPropertyName(), "byte=0-3");

        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/partial_content.txt")
                .withRequestLine(GET.name())
                .withHeaderParameters(headerParams)
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(PARTIAL_CONTENT));
    }

    @Test
    public void routesGetPatchContentToReadResource() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/patch-content.txt")
                .withRequestLine(GET.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(OK));
        assertThat(resourceHandlerSpy.hasReadResource(), is(true));
    }

    @Test
    public void routesPatchContentToPatchResource() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/patch-content.txt")
                .withRequestLine(PATCH.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(PRECONDITION_FAILED));
    }
}
