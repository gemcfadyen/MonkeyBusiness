package server;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HttpServerTest {

    private ServerSocketSpy serverSocketSpy;
    private RequestParserSpy httpRequestParserSpy;
    private HttpServer httpServer;
    private ClientSpy clientSpy;
    private RouteProcessorSpy httpRequestProcessorSpy;
    private ExecutorServiceFactorySpy executorServiceFactorySpy;
    private ExecutorServiceSpy executorServiceSpy;

    @Before
    public void setUp() throws Exception {
        clientSpy = new ClientSpy();
        serverSocketSpy = new ServerSocketSpy(clientSpy);
        httpRequestParserSpy = new RequestParserSpy();
        httpRequestProcessorSpy = new RouteProcessorSpy();
        executorServiceSpy = new ExecutorServiceSpy();
        executorServiceFactorySpy = new ExecutorServiceFactorySpy(executorServiceSpy);
        httpServer = new HttpServer(serverSocketSpy,
                httpRequestParserSpy,
                httpRequestProcessorSpy,
                executorServiceFactorySpy
        );
    }

    @Test
    public void whenServerIsProcessingItCreatesThreadpool() {
        httpServer.processRequest();

        assertThat(executorServiceFactorySpy.hasInitialisedThreadPool(), is(true));
    }

    @Test
    public void whenServerIsProcessingItExecutesRunnableTask() {
        httpServer.processRequest();

        assertThat(serverSocketSpy.isAcceptingRequests(), is(true));
        assertThat(executorServiceSpy.hasExecuted(), is(true));
    }

    @Test
    public void threadExecutorServiceShutsdown() {
        httpServer.processRequest();

        assertThat(executorServiceSpy.hasShutdodwn(), is(true));
    }
}
