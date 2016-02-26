package server.actions;

import org.junit.Test;
import server.ResourceHandlerSpy;
import server.StatusCode;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static server.HttpMethods.DELETE;
import static server.messages.HttpRequestBuilder.anHttpRequestBuilder;

public class DeleteResourceTest {
    private ResourceHandlerSpy resourceHandlerSpy = new ResourceHandlerSpy();
    private DeleteResource deleteResource = new DeleteResource(resourceHandlerSpy);

    @Test
    public void deleteRequestRemovesResource() {
        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/form")
                .withRequestLine(DELETE.name())
                .build();

        HttpResponse httpResponse = deleteResource.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.OK));
        assertThat(resourceHandlerSpy.hasDeletedResource(), is(true));
    }
}