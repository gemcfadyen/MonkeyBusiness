import org.hamcrest.Matchers;
import org.junit.Test;

import static org.junit.Assert.assertThat;

public class HttpServerTest {

    @Test
    public void serverHasAHost() {
        HttpServer httpServer = new HttpServer("localhost");

        assertThat(httpServer.getHost(), Matchers.is("localhost"));
    }
}
