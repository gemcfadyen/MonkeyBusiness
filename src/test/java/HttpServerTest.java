import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HttpServerTest {

    private ServerSocketSpy serverSocketSpy;
    private HttpRequestParserSpy httpRequestParserSpy;
    private HttpResponseBuilderSpy httpResponseBuilderSpy;
    private HttpServer httpServer;
    private ClientSpy clientSpy;

    @Before
    public void setUp() throws Exception {
        clientSpy = new ClientSpy();
        serverSocketSpy = new ServerSocketSpy(clientSpy);
        httpRequestParserSpy = new HttpRequestParserSpy();
        httpResponseBuilderSpy = new HttpResponseBuilderSpy();
        httpServer = new HttpServer("localhost", 8080, serverSocketSpy, httpRequestParserSpy, httpResponseBuilderSpy);
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
    public void serverParsesClientRequest() {
        httpServer.start();

        assertThat(httpRequestParserSpy.hasParsedRequest(), is(true));
    }

    @Test
    public void createsHttpResponse() {
        httpServer.start();

        assertThat(httpResponseBuilderSpy.hasBuiltHttpResponse(), is(true));
    }

    @Test
    public void providesClientWithHttpResponse() {
        httpServer.start();

        assertThat(clientSpy.hasHttpResponse(), is(true));
    }

    @Test
    public void clientConnectionIsClosed() {
        httpServer.start();

        assertThat(clientSpy.isClosed(), is(true));
    }
}
