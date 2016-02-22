import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HttpServerTest {

    private ServerSocketSpy serverSocketSpy;
    private HttpRequestParserSpy httpRequestParserSpy;
    private HttpServer httpServer;

    @Before
    public void setUp() throws Exception {
        serverSocketSpy = new ServerSocketSpy();
        httpRequestParserSpy = new HttpRequestParserSpy();
        httpServer = new HttpServer("localhost", 8080, serverSocketSpy, httpRequestParserSpy);
    }

    @Test
    public void serverHasAHost() {
        assertThat(httpServer.getHost(), is("localhost"));
    }

    @Test
    public void serverHasAPort() {
        assertThat(httpServer.getPort(), is(8080));
    }

    @Test
    public void whenServerIsStartedItAcceptsClientRequests() {
        httpServer.start();

        assertThat(serverSocketSpy.isAcceptingRequests(), is(true));
    }

    @Test
    public void serverAcceptsClientRequest() {
        httpServer.start();

        assertThat(httpRequestParserSpy.hasParsedRequest(), is(true));
    }
}
