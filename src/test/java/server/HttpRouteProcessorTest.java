package server;

import org.junit.Before;
import org.junit.Test;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static server.HttpMethods.*;
import static server.StatusCode.*;
import static server.messages.HttpRequestBuilder.anHttpRequestBuilder;

public class HttpRouteProcessorTest {
    private HttpRouteProcessor requestProcessor;
    private ResourceHandlerSpy resourceHandlerSpy;

    @Before
    public void setup() {
        resourceHandlerSpy = new ResourceHandlerSpy();
        requestProcessor = new HttpRouteProcessor(resourceHandlerSpy);
    }

    @Test
    public void provides404WhenNoRoutesMet() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/unknown/route")
                .withRequestLine(GET.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(NOT_FOUND));
    }

    @Test
    public void listsDirectoryContent() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/")
                .withRequestLine(GET.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(OK));
        assertThat(resourceHandlerSpy.hasGotDirectoryContent(), is(true));
    }

    @Test
    public void simplePutReturnsCode200() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/form")
                .withBody("image")
                .withRequestLine(POST.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(OK));
    }

    @Test
    public void simpleOptionReturns200Code() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/method_options")
                .withRequestLine(OPTIONS.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(OK));
    }

    @Test
    public void simpleOptionReturnsMethodsInAllow() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/method_options")
                .withRequestLine(OPTIONS.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.allowedMethods(), containsInAnyOrder(GET, HEAD, POST, OPTIONS, PUT, DELETE));
    }

    @Test
    public void getMethodLooksUpResourceForResponseBody() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/form")
                .withRequestLine(GET.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(OK));
        assertThat(resourceHandlerSpy.hasReadResource(), is(true));
    }

    @Test
    public void postMethodCreatesAResource() {
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
    public void putMethodUpdatesAResource() {
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
    public void deleteMethodRemovesResource() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/form")
                .withRequestLine(DELETE.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(OK));
        assertThat(resourceHandlerSpy.hasDeletedResource(), is(true));
    }

    @Test
    public void redirectReturns302() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/redirect")
                .withRequestLine(GET.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(FOUND));
        assertThat(httpResponse.location(), is("http://localhost:5000/"));
    }

    @Test
    public void getImageContentForJpeg() {
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
    public void getImageContentForPng() {
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
    public void getImageContentForGif() {
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
    public void getFileContents() {
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
    public void methodNotAllowed() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/file1")
                .withRequestLine(PUT.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(METHOD_NOT_ALLOWED));
    }

    @Test
    public void bogusRequestResultsInMethodNotAllowed() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/file1")
                .withRequestLine("BOGUS_METHOD")
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(METHOD_NOT_ALLOWED));
    }

    @Test
    public void readsResourceFromTextFile() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/text-file.txt")
                .withRequestLine(GET.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(OK));
    }

    @Test
    public void bogusRequestAtRouteTextFile() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/text-file.txt")
                .withRequestLine(POST.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(METHOD_NOT_ALLOWED));
    }
}
