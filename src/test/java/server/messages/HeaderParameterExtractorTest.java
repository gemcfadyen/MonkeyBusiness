package server.messages;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static server.messages.HttpMessageHeaderProperties.AUTHORISATION;
import static server.messages.HttpMessageHeaderProperties.PARTIAL_CONTENT_RANGE;

public class HeaderParameterExtractorTest {
    private HeaderParameterExtractor headerParameterExtractor = new HeaderParameterExtractor();

    @Test
    public void extractsAuthenticationCredentials() {
        Map<String, String> headerParams = new HashMap<>();
        headerParams.put(AUTHORISATION.getPropertyName(), "Basic someEncodedValue");

        String authenticationCredentials = headerParameterExtractor.getAuthenticationCredentials(headerParams);

        assertThat(authenticationCredentials, is("Basic someEncodedValue"));
    }

    @Test
    public void upperAndLowerBoundariesProvidedForPartialContent() {
        Map<String, String> headerParams = new HashMap<>();
        headerParams.put(PARTIAL_CONTENT_RANGE.getPropertyName(), "bytes=0-4");

        Range partialContentRange = headerParameterExtractor.getPartialContentRange(headerParams, "resource-content".length());

        assertThat(partialContentRange.getStartingIndex(), is(0));
        assertThat(partialContentRange.getFinishingIndex(), is(4));
    }

    @Test
    public void partialContentWithNoLowerBoundaryIsCorrectlyDetermined() {
        Map<String, String> headerParams = new HashMap<>();
        headerParams.put(PARTIAL_CONTENT_RANGE.getPropertyName(), "bytes=-4");

        Range partialContentRange = headerParameterExtractor.getPartialContentRange(headerParams, "resource-content".length());

        assertThat(partialContentRange.getStartingIndex(), is(12));
        assertThat(partialContentRange.getFinishingIndex(), is("resource-content".length()));
    }

    @Test
    public void partialContentWithNoUpperBoundaryIsSetToLengthOfResource() {
        Map<String, String> headerParams = new HashMap<>();
        headerParams.put(PARTIAL_CONTENT_RANGE.getPropertyName(), "bytes=4-");

        Range partialContentRange = headerParameterExtractor.getPartialContentRange(headerParams, "resource-content".length());

        assertThat(partialContentRange.getStartingIndex(), is(4));
        assertThat(partialContentRange.getFinishingIndex(), is("resource-content".length()));
    }
}