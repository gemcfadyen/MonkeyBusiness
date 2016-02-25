import org.junit.Before;
import org.junit.Test;

import static java.util.Collections.EMPTY_MAP;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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
        HttpRequest httpRequest = new HttpRequest(HttpMethods.GET.name(), "/unknown/route", EMPTY_MAP, "");

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.NOT_FOUND));
    }

    @Test
    public void simpleGetReturnsCode200() {
        HttpRequest httpRequest = new HttpRequest(HttpMethods.GET.name(), "/", EMPTY_MAP, "");

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.OK));
    }

    @Test
    public void simplePutReturnsCode200() {
        HttpRequest httpRequest = new HttpRequest(HttpMethods.POST.name(), "/form", EMPTY_MAP, "");
        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.OK));
    }

    @Test
    public void simpleOptionReturns200Code() {
        HttpRequest httpRequest = new HttpRequest(HttpMethods.OPTIONS.name(), "/method_options", EMPTY_MAP, "");
        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.OK));
    }

    @Test
    public void simpleOptionReturnsMethodsInAllow() {
        HttpRequest httpRequest = new HttpRequest(HttpMethods.OPTIONS.name(), "/method_options", EMPTY_MAP, "");
        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.allowedMethods(), containsInAnyOrder(HttpMethods.GET, HttpMethods.HEAD, HttpMethods.POST, HttpMethods.OPTIONS, HttpMethods.PUT, HttpMethods.DELETE));
    }

    @Test
    public void getMethodLooksUpResourceForResponseBody() {
        HttpRequest httpRequest = new HttpRequest(HttpMethods.GET.name(), "/form", EMPTY_MAP, "");
        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.OK));
        assertThat(resourceHandlerSpy.hasReadResource(), is(true));
    }

    @Test
    public void postMethodCreatesAResource() {
        HttpRequest httpRequest = new HttpRequest(HttpMethods.POST.name(), "/form", EMPTY_MAP, "content");
        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.OK));
        assertThat(httpResponse.body(), is("content".getBytes()));
        assertThat(resourceHandlerSpy.hasWrittenToResource(), is(true));
    }

    @Test
    public void putMethodUpdatesAResource() {
        HttpRequest httpRequest = new HttpRequest(HttpMethods.PUT.name(), "/form", EMPTY_MAP, "content");
        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.OK));
        assertThat(httpResponse.body(), is("content".getBytes()));
        assertThat(resourceHandlerSpy.hasWrittenToResource(), is(true));
    }

    @Test
    public void deleteMethodRemovesResource() {
        HttpRequest httpRequest = new HttpRequest(HttpMethods.DELETE.name(), "/form", EMPTY_MAP, "");
        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.OK));
        assertThat(resourceHandlerSpy.hasDeletedResource(), is(true));
    }

    @Test
    public void redirectReturns302() {
        HttpRequest httpRequest = new HttpRequest(HttpMethods.GET.name(), "/redirect", EMPTY_MAP, "");
        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.FOUND));
        assertThat(httpResponse.location(), is("http://localhost:5000/"));
    }

    @Test
    public void getImageContentForJpeg() {
        HttpRequest httpRequest = new HttpRequest(HttpMethods.GET.name(), "/image.jpeg", EMPTY_MAP, "");
        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.OK));
        assertThat(httpResponse.body(), is("My=Data".getBytes()));
        assertThat(resourceHandlerSpy.hasReadResource(), is(true));
    }

    @Test
    public void getImageContentForPng() {
        HttpRequest httpRequest = new HttpRequest(HttpMethods.GET.name(), "/image.png", EMPTY_MAP, "");
        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.OK));
        assertThat(httpResponse.body(), is("My=Data".getBytes()));
        assertThat(resourceHandlerSpy.hasReadResource(), is(true));
    }

    @Test
    public void getImageContentForGif() {
        HttpRequest httpRequest = new HttpRequest(HttpMethods.GET.name(), "/image.gif", EMPTY_MAP, "");
        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.OK));
        assertThat(httpResponse.body(), is("My=Data".getBytes()));
        assertThat(resourceHandlerSpy.hasReadResource(), is(true));
    }

}
