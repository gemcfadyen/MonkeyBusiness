package server.actions;

import server.Action;
import server.DelimitedFormatter;
import server.ResourceHandler;
import server.StatusCode;
import server.messages.HttpRequest;
import server.messages.HttpResponse;
import server.messages.HttpResponseBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static server.Delimiter.BREAK;

public class ListResourcesInPublicDirectory implements Action {
    private ResourceHandler resourceHandler;
    private final DelimitedFormatter listFormatter;

    public ListResourcesInPublicDirectory(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
        listFormatter = new DelimitedFormatter<String>();
    }

    public HttpResponse process(HttpRequest request) {
        return HttpResponseBuilder
                .anHttpResponseBuilder()
                .withStatusCode(StatusCode.OK)
                .withBody(getDelimitedContentsOfDirectory().getBytes())
                .build();
    }

    private String getDelimitedContentsOfDirectory() {
        String[] filenames = resourceHandler.directoryContent();
        return listFormatter.delimitedValues(Arrays.asList(transformToLinks(filenames)), BREAK);
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
