package server.actions;

import server.Action;
import server.ResourceHandler;
import server.StatusCode;
import server.messages.HttpRequest;
import server.messages.HttpResponse;
import server.messages.HttpResponseBuilder;

import java.util.Arrays;

public class ListResourcesInPublicDirectory implements Action {
    private ResourceHandler resourceHandler;

    public ListResourcesInPublicDirectory(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    public HttpResponse process(HttpRequest request) {
        return HttpResponseBuilder
                .anHttpResponseBuilder()
                .withStatusCode(StatusCode.OK)
                .withBody(getCommaSeparatedContentsOfDirectory().getBytes())
                .build();
    }

    private String getCommaSeparatedContentsOfDirectory() {
        String[] filenames = resourceHandler.directoryContent();

        FormatListAsCommaDelimitedContent listFormatter = new FormatListAsCommaDelimitedContent<String>();
        String s = listFormatter.commaDelimitAllowParameters(Arrays.asList(filenames));
        System.out.println(s);
        return s;
    }
}
