package pgtips.gamoflife;

import org.junit.Test;
import pgtips.gameoflife.MonkeyBusiness;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static server.messages.HttpRequestBuilder.anHttpRequestBuilder;
import static server.messages.StatusCode.OK;
import static server.router.HttpMethods.GET;

public class MonkeyBusinessTest  {
    @Test
    public void generatesResponse() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/form")
                .withRequestLine(GET.name())
                .build();

        MonkeyBusiness readResource = new MonkeyBusiness(0);
        HttpResponse httpResponse = readResource.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(OK));
// TODO Georgina said she would fix it :D
//        assertThat(new String(httpResponse.body()), is("My=Data"));
    }
}