import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

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
        HttpRequest httpRequest = new HttpRequest("get", "/unknown/route", Collections.EMPTY_MAP, "");

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(404));
    }

    @Test
    public void simpleGetReturnsCode200() {
        HttpRequest httpRequest = new HttpRequest("get", "/", Collections.EMPTY_MAP, "");

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(200));
    }

    @Test
    public void simplePutReturnsCode200() {
        HttpRequest httpRequest = new HttpRequest("post", "/form", Collections.EMPTY_MAP, "");
        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(200));
    }

    @Test
    public void simpleOptionReturns200Code() {
        HttpRequest httpRequest = new HttpRequest("options", "/method_options", Collections.EMPTY_MAP, "");
        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(200));
    }

    @Test
    public void simpleOptionReturnsMethodsInAllow() {
        HttpRequest httpRequest = new HttpRequest("options", "/method_options", Collections.EMPTY_MAP, "");
        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.allowedMethods(), containsInAnyOrder("GET", "HEAD", "POST", "OPTIONS", "PUT"));
    }

//    @Test
//    public void postingContentCreatesResource() {
//        HttpRequest httpRequest = new HttpRequest("post", "/form");
//
//        /***
//         * POST /path/script.cgi HTTP/1.0
//         From: frog@jmarshall.com
//         User-Agent: HTTPTool/1.0
//         Content-Type: application/x-www-form-urlencoded
//         Content-Length: 32
//
//         home=Cosby&favorite+flavor=flies
//         */
//        HttpResponse httpResponse = requestProcessor.process(httpRequest);
//
//        assertThat(httpResponse.allowedMethods(), containsInAnyOrder("GET", "HEAD", "POST", "OPTIONS", "PUT"));
//    }
}
