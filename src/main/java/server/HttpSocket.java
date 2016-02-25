package server;

import java.io.InputStream;

public interface HttpSocket {
    InputStream getRawHttpRequest();
    void close();
    void setHttpResponse(HttpResponse httpResponse);
}
