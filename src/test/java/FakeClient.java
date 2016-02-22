import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class FakeClient implements HttpSocket {

    @Override
    public InputStream getRawHttpRequest() {
        return new ByteArrayInputStream("An Http Request".getBytes());
    }
}
