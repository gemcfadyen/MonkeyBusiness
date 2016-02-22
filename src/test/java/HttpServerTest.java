import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HttpServerTest {

    @Test
    public void serverHasAHost() {
        HttpServer httpServer = new HttpServer("localhost", 1);

        assertThat(httpServer.getHost(), is("localhost"));
    }

    @Test
    public void serverHasAPort() {
        HttpServer httpServer = new HttpServer("localhost", 8080);

        assertThat(httpServer.getPort(), is(8080));
    }
}
