package server.actions;

import server.Action;
import server.ResourceHandler;
import server.StatusCode;
import server.messages.HttpRequest;
import server.messages.HttpResponse;
import server.messages.HttpResponseBuilder;

public class ListResourcesInPublicDirectory implements Action {
    private ResourceHandler resourceHandler;

    public ListResourcesInPublicDirectory(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    public HttpResponse process(HttpRequest request) {
        String[] filenames = resourceHandler.directoryContent();

      String allFilenames = filenames[0];
        for (int i = 1; i < filenames.length; i++) {
           allFilenames += filenames + ", " + filenames[i];
        }

        //TODO extract out the above

        return HttpResponseBuilder
                .anHttpResponseBuilder()
                .withStatusCode(StatusCode.OK)
                .withBody(allFilenames.getBytes())
                .build();
    }
}
