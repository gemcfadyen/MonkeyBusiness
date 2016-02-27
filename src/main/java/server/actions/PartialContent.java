package server.actions;

import server.Action;
import server.ResourceHandler;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
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
        String contentRange = headerParams.get("Range");
        System.out.println("content range is " + contentRange);
//TODO move to a parameter extracting class
        String[] byteRange = contentRange.split("=");
        byte[] readResource = resourceHandler.read(request.getRequestUri());
        try {
            System.out.println("Content read is: " + new String(readResource, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int startingIndex;
        int finishingIndex;
        String[] startingByteAndFinishingByte = byteRange[1].split("-");

        if (startingByteAndFinishingByte.length == 2 && !startingByteAndFinishingByte[0].equals("")) {
            System.out.println("both are provided...");
            String startingByte = startingByteAndFinishingByte[0];
            startingIndex = Integer.valueOf(startingByte);
            System.out.println("first is: " + startingByte);
            String finishingByte = startingByteAndFinishingByte[1];
            finishingIndex = Integer.valueOf(finishingByte);
            System.out.println("second is" + finishingByte);
        } else if (startingByteAndFinishingByte.length == 2) {
            startingIndex = readResource.length - Integer.valueOf(startingByteAndFinishingByte[1]);
            finishingIndex = readResource.length;
        } else {
            System.out.println("only first one provided");
            String startingByte = startingByteAndFinishingByte[0];
            startingIndex = Integer.valueOf(startingByte);
            finishingIndex = readResource.length;

        }

        byte[] portionToReturn = Arrays.copyOfRange(readResource, startingIndex, checkForEndOfResource(readResource.length, finishingIndex));
        try {
            System.out.println("PORTION RETURNED IS [" + new String(portionToReturn, "UTF-8") + "]");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return anHttpResponseBuilder()
                .withStatusCode(PARTIAL_CONTENT)
                .withContentRange(startingIndex, finishingIndex)
                .withBody(portionToReturn)
                .build();
    }

    private int checkForEndOfResource(int length, int finishingIndex) {
        if(finishingIndex + 1 > length) {
            return length;
        }
        return finishingIndex+1;
    }
}
