package server.actions;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class EtagDictionaryTest {

    private final EtagDictionary etagDictionary = new EtagDictionary();

    @Test
    public void findsEtagForDefaultContent() {
        assertThat(etagDictionary.has("default content".getBytes()), is(true));
    }

    @Test
    public void findsEtagForPatchedContent() {
        assertThat(etagDictionary.has("patched content".getBytes()), is(true));
    }

    @Test
    public void doesNotFindAValueThatIsNotInDictionary() {
        assertThat(etagDictionary.has("another content".getBytes()), is(false));
    }

    @Test
    public void returnsFalseWhenEncodingExceptionIsThrown() {
        EtagDictionary etagDictionaryWhichThrowsEncodingException = new EtagDictionary() {
            protected boolean isInDictionary(byte[] value) throws UnsupportedEncodingException {
                throw new UnsupportedEncodingException("Throws exception for test");
            }
        };
        assertThat(etagDictionaryWhichThrowsEncodingException.has("anything".getBytes()), is(false));
    }
}
