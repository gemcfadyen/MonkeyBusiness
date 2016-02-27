package server.actions;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EtagGenerator {

    public String calculateEtag(byte[] content) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(content, 0, content.length);
            byte[] digest = messageDigest.digest();
            return new HexBinaryAdapter().marshal(digest).toLowerCase();

        } catch (NoSuchAlgorithmException e) {
            throw new EtagGeneratorException("Exception in generating etag", e);
        }
    }
}
