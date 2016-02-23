import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HttpRequestProcessorTest {

    @Test
    public void provides404WhenNoRoutesMet() {
        HttpRequestProcessor requestProcessor = new HttpRequestProcessor();
        HttpRequest httpRequest = new HttpRequest("get", "/unknown/route");

        HttpResponse httpResponse = requestProcessor.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(404));
    }
}
