package server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private final String host;
    private final int port;
    private final HttpServerSocket serverSocket;
    private final RequestParser requestParser;
    private RouteProcessor httpRouteProcessor;
    private ExecutorServiceFactory executorServiceFactory;

    public HttpServer(String host, int port,
                      HttpServerSocket serverSocket,
                      RequestParser requestParser,
                      RouteProcessor httpRouteProcessor,
                      ExecutorServiceFactory requestExecutorService) {
        this.host = host;
        this.port = port;
        this.serverSocket = serverSocket;
        this.requestParser = requestParser;
        this.httpRouteProcessor = httpRouteProcessor;
        this.executorServiceFactory = requestExecutorService;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void processRequest() {
        System.out.println("Listening for request.....");
        ExecutorService executor = executorServiceFactory.create();
        ProcessClientRequestTask task = new ProcessClientRequestTask(serverSocket.accept(), requestParser, httpRouteProcessor);
        ThreadExecutorService requestProcessorThread = new RequestThreadExecutorService(executor);
        requestProcessorThread.execute(task);
        requestProcessorThread.shutdown();
    }
}

interface ExecutorServiceFactory {
    ExecutorService create();
}

class FixedThreadPoolExecutorService implements ExecutorServiceFactory {
    private final int numberOfThreads;

    public FixedThreadPoolExecutorService(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public ExecutorService create() {
        return Executors.newFixedThreadPool(numberOfThreads);
    }
}


interface ThreadExecutorService {
    void execute(Runnable r);

    void shutdown();
}

class RequestThreadExecutorService implements ThreadExecutorService {

    private final ExecutorService executor;

    public RequestThreadExecutorService(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public void execute(Runnable task) {
        executor.execute(task);
    }

    @Override
    public void shutdown() {
        executor.shutdown();
    }
}