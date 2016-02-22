import java.io.InputStream;

public interface HttpSocket {
    InputStream getRawHttpRequest();
    void close();
}
