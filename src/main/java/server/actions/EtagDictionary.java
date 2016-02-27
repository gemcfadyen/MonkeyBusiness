package server.actions;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class EtagDictionary {

    private Map<String, String> etagDictionary = new HashMap<>();

    public EtagDictionary() {
        etagDictionary.put("default content", "dc50a0d27dda2eee9f65644cd7e4c9cf11de8bec");
        etagDictionary.put("patched content", "5c36acad75b78b82be6d9cbbd6143ab7e0cc04b0");
    }

    public boolean has(byte[] value) {
        try {
            return isInDictionary(value);
        } catch (UnsupportedEncodingException e) {
            return false;
        }
    }

    protected boolean isInDictionary(byte[] value) throws UnsupportedEncodingException {
        return etagDictionary.get(new String(value, "UTF-8")) != null;
    }
}
