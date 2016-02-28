package server.actions;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class EtagGeneratorTest {

    private final EtagGenerator etagGenerator = new EtagGenerator();
    @Test
    public void calculateEtagForDefaultContent() {
        String etag = etagGenerator.calculateEtag("default content".getBytes());
        assertThat(etag, is("dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec"));
    }

    @Test
    public void calculateEtagForPatchedContent() {
        String etag = etagGenerator.calculateEtag("patched content".getBytes());
        assertThat(etag, is("5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0"));
    }
}
