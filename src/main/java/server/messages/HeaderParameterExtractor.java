package server.messages;

import server.Range;

import java.util.Map;

import static server.messages.RequestHeaderProperties.AUTHORISATION;
import static server.messages.RequestHeaderProperties.PARTIAL_CONTENT_RANGE;

public class HeaderParameterExtractor {


   public String getAuthenticationCredentials(Map<String, String> headerParams) {
      return headerParams.get(AUTHORISATION.getPropertyName());
   }

    public Range getPartialContentRange(Map<String, String> headerParams, int resourceLength) {
        String contentRange = getRangeProperty(headerParams);
        String byteRange = getRangeReceived(contentRange);
        String[] providedStartAndFinishRange = splitStartAndFinishIndexesReceived(byteRange);

        if (rangeIsFullyProvided(providedStartAndFinishRange)) {
            return createRangeFrom(providedStartAndFinishRange);
        } else if (onlyFinishIndexProvided(providedStartAndFinishRange)) {
            return createRangeWithDerivedStartingIndex(resourceLength, providedStartAndFinishRange);
        } else {
            return createRangeWithDerivedFinishIndex(resourceLength, providedStartAndFinishRange);
        }
    }

    private Range createRangeWithDerivedFinishIndex(int resourceLength, String[] providedStartAndFinishRange) {
        String startingByte = providedStartAndFinishRange[0];
        return new Range(Integer.valueOf(startingByte), resourceLength);
    }

    private Range createRangeWithDerivedStartingIndex(int resourceLength, String[] providedStartAndFinishRange) {
        int startingIndex = resourceLength - Integer.valueOf(providedStartAndFinishRange[1]);
        return new Range(startingIndex, resourceLength);
    }

    private boolean onlyFinishIndexProvided(String[] providedStartAndFinishRange) {
        return providedStartAndFinishRange.length == 2;
    }

    private Range createRangeFrom(String[] startingByteAndFinishingByte) {
        String startingByte = startingByteAndFinishingByte[0];
        String finishingByte = startingByteAndFinishingByte[1];
        return new Range(Integer.valueOf(startingByte), Integer.valueOf(finishingByte));
    }

    private boolean rangeIsFullyProvided(String[] startingByteAndFinishingByte) {
        return startingByteAndFinishingByte.length == 2 && !startingByteAndFinishingByte[0].equals("");
    }

    private String[] splitStartAndFinishIndexesReceived(String byteRange) {
        return byteRange.split("-");
    }

    private String getRangeReceived(String contentRange) {
        String[] byteRange = contentRange.split("=");
        return byteRange[1];
    }

    private String getRangeProperty(Map<String, String> headerParams) {
        return headerParams.get(PARTIAL_CONTENT_RANGE.getPropertyName());
    }
}

