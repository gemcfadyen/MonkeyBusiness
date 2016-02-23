import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HttpRequestProcessorTest {

    private HttpRequestProcessor requestProcessor;

    @Before
    public void setup() {
        requestProcessor = new HttpRequestProcessor();
    }

    @Test
    public void provides404WhenNoRoutesMet() {
        HttpRequest httpRequest = new HttpRequest("get", "/unknown/route");

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(404));
    }

    @Test
    public void simpleGetReturnsCode200() {
        HttpRequest httpRequest = new HttpRequest("get", "/");

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(200));
    }

    @Test
    public void simplePutReturnsCode200() {
        HttpRequest httpRequest = new HttpRequest("post", "/form");
        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(200));
    }

    @Test
    public void simpleOptionReturns200Code() {
        HttpRequest httpRequest = new HttpRequest("options", "/method_options");
        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(200));
    }

    @Test
    public void simpleOptionReturnsMethodsInAllow() {
        HttpRequest httpRequest = new HttpRequest("options", "/method_options");
        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.allowedMethods(), containsInAnyOrder("GET", "HEAD", "POST", "OPTIONS", "PUT"));
    }
}
