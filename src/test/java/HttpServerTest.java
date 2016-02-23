import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HttpServerTest {

    private ServerSocketSpy serverSocketSpy;
    private RequestParserSpy httpRequestParserSpy;
    private ResponseBuilderSpy httpResponseBuilderSpy;
    private HttpServer httpServer;
    private ClientSpy clientSpy;

    @Before
    public void setUp() throws Exception {
        clientSpy = new ClientSpy();
        serverSocketSpy = new ServerSocketSpy(clientSpy);
        httpRequestParserSpy = new RequestParserSpy();
        httpResponseBuilderSpy = new ResponseBuilderSpy();
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
        httpServer.processRequest();

        assertThat(serverSocketSpy.isAcceptingRequests(), is(true));
    }

    @Test
    public void serverParsesClientRequest() {
        httpServer.processRequest();

        assertThat(httpRequestParserSpy.hasParsedRequest(), is(true));
    }

    @Test
    public void createsHttpResponse() {
        httpServer.processRequest();

        assertThat(httpResponseBuilderSpy.hasBuiltHttpResponse(), is(true));
        assertThat(httpResponseBuilderSpy.hasGotStatusCode(), is(true));
        assertThat(httpResponseBuilderSpy.hasGotReasonPhrase(), is(true));
    }

    @Test
    public void providesClientWithHttpResponse() {
        httpServer.processRequest();

        assertThat(clientSpy.hasHttpResponse(), is(true));
    }

    @Test
    public void clientConnectionIsClosed() {
        httpServer.processRequest();

        assertThat(clientSpy.isClosed(), is(true));
    }
}
