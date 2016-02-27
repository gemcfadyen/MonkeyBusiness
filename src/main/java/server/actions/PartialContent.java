package server.actions;

import server.Action;
import server.ResourceHandler;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import java.util.Map;

import static server.StatusCode.PARTIAL_CONTENT;
import static server.messages.HttpResponseBuilder.anHttpResponseBuilder;

public class PartialContent implements Action {
    private ResourceHandler resourceHandler;

    public PartialContent(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    @Override
    public HttpResponse process(HttpRequest request) {
        Map<String, String> headerParams = request.headerParameters();
        //TODO move into a parameter extractor class
        String contentRange = headerParams.get("Range");
        System.out.println("content range is " + contentRange);
        String[] byteRange = contentRange.split("=");
        String[] startingByteAndFinishingByte = byteRange[1].split("-");
        String startingByte = startingByteAndFinishingByte[0];
        String finishingByte = startingByteAndFinishingByte[1];

        int startingIndex = Integer.valueOf(startingByte);
        int finishingIndex = Integer.valueOf(finishingByte);

        byte[] portionOfResourceRead = resourceHandler.readByteRange(request.getRequestUri(), startingIndex, finishingIndex);

        return anHttpResponseBuilder()
                .withStatusCode(PARTIAL_CONTENT)
                .withContentRange(startingIndex, finishingIndex)
                .withBody(portionOfResourceRead)
                .build();
    }
}
