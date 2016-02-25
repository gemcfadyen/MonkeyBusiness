package server;

import org.junit.Before;
import org.junit.Test;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
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
                .withRequestLine(HttpMethods.GET.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.NOT_FOUND));
    }

    @Test
    public void listsDirectoryContent() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/")
                .withRequestLine(HttpMethods.GET.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.OK));
        assertThat(httpResponse.body(), is("file".getBytes()));
        assertThat(resourceHandlerSpy.hasGotDirectoryContent(), is(true));
    }

    @Test
    public void simplePutReturnsCode200() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/form")
                .withBody("image")
                .withRequestLine(HttpMethods.POST.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.OK));
    }

    @Test
    public void simpleOptionReturns200Code() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/method_options")
                .withRequestLine(HttpMethods.OPTIONS.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.OK));
    }

    @Test
    public void simpleOptionReturnsMethodsInAllow() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/method_options")
                .withRequestLine(HttpMethods.OPTIONS.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.allowedMethods(), containsInAnyOrder(HttpMethods.GET, HttpMethods.HEAD, HttpMethods.POST, HttpMethods.OPTIONS, HttpMethods.PUT, HttpMethods.DELETE));
    }

    @Test
    public void getMethodLooksUpResourceForResponseBody() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/form")
                .withRequestLine(HttpMethods.GET.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.OK));
        assertThat(resourceHandlerSpy.hasReadResource(), is(true));
    }

    @Test
    public void postMethodCreatesAResource() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/form")
                .withBody("content")
                .withRequestLine(HttpMethods.POST.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.OK));
        assertThat(httpResponse.body(), is("content".getBytes()));
        assertThat(resourceHandlerSpy.hasWrittenToResource(), is(true));
    }

    @Test
    public void putMethodUpdatesAResource() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/form")
                .withRequestLine(HttpMethods.PUT.name())
                .withBody("content")
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.OK));
        assertThat(httpResponse.body(), is("content".getBytes()));
        assertThat(resourceHandlerSpy.hasWrittenToResource(), is(true));
    }

    @Test
    public void deleteMethodRemovesResource() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/form")
                .withRequestLine(HttpMethods.DELETE.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.OK));
        assertThat(resourceHandlerSpy.hasDeletedResource(), is(true));
    }

    @Test
    public void redirectReturns302() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/redirect")
                .withRequestLine(HttpMethods.GET.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.FOUND));
        assertThat(httpResponse.location(), is("http://localhost:5000/"));
    }

    @Test
    public void getImageContentForJpeg() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/image.jpeg")
                .withRequestLine(HttpMethods.GET.name())
                .withBody("My=Data")
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.OK));
        assertThat(httpResponse.body(), is("My=Data".getBytes()));
        assertThat(resourceHandlerSpy.hasReadResource(), is(true));
    }

    @Test
    public void getImageContentForPng() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/image.png")
                .withRequestLine(HttpMethods.GET.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.OK));
        assertThat(httpResponse.body(), is("My=Data".getBytes()));
        assertThat(resourceHandlerSpy.hasReadResource(), is(true));
    }

    @Test
    public void getImageContentForGif() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/image.gif")
                .withRequestLine(HttpMethods.GET.name())
                .build();

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.OK));
        assertThat(httpResponse.body(), is("My=Data".getBytes()));
        assertThat(resourceHandlerSpy.hasReadResource(), is(true));
    }

}
