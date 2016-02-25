package server.actions;

import server.Action;
import server.ResourceHandler;
import server.StatusCode;
import server.messages.HttpRequest;
import server.messages.HttpResponse;
import server.messages.HttpResponseBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        return listFormatter.commaDelimitAllowParameters(Arrays.asList(transformToLinks(filenames)));
    }

    private String[] transformToLinks(String[] filenames) {
        return asLinks(filenames).toArray(new String[filenames.length]);
    }

    private List<String> asLinks(String[] filenames) {
        List<String> links = new ArrayList<>();
        for (String filename : filenames) {
            links.add(String.format("<a href=/%s>%s</a>", filename, filename));
        }
        return links;
    }
}
