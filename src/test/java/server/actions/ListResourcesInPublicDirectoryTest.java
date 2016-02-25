package server.actions;

import org.junit.Test;
import server.HttpMethods;
import server.ResourceHandlerSpy;
import server.StatusCode;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static server.messages.HttpRequestBuilder.anHttpRequestBuilder;

public class ListResourcesInPublicDirectoryTest {
    @Test
    public void filnames() {
        ResourceHandlerSpy resourceHandlerSpy = new ResourceHandlerSpy();
        ListResourcesInPublicDirectory listResources = new ListResourcesInPublicDirectory(resourceHandlerSpy);

        HttpRequest httpRequest = anHttpRequestBuilder()
                .withRequestUri("/")
                .withRequestLine(HttpMethods.GET.name())
                .build();

        HttpResponse httpResponse = listResources.process(httpRequest);

        assertThat(httpResponse.statusCode(), is(StatusCode.OK));
        assertThat(httpResponse.body(), is("file1,file2".getBytes()));
        assertThat(resourceHandlerSpy.hasGotDirectoryContent(), is(true));
    }


}
