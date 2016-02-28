package server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private final String host;
    private final int port;
    private final HttpServerSocket serverSocket;
    private final RequestParser requestParser;
    private RouteProcessor httpRouteProcessor;
    private EService maservice;

    public HttpServer(String host, int port,
                      HttpServerSocket serverSocket,
                      RequestParser requestParser,
                      RouteProcessor httpRouteProcessor,
                      RequestExecutorService requestExecutorService) {
        this.host = host;
        this.port = port;
        this.serverSocket = serverSocket;
        this.requestParser = requestParser;
        this.httpRouteProcessor = httpRouteProcessor;
        this.maservice = requestExecutorService;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public void processRequest() {
        System.out.println("Listening for request.....");

        ExecutorService executor = maservice.initialise();

        ProcessClientRequestTask task = new ProcessClientRequestTask(serverSocket.accept(), requestParser, httpRouteProcessor);
        Executor ce = new RequestExecutor(executor);
        ce.execute(task);
        ce.shutdown();
    }
}

interface EService {
    ExecutorService initialise();
}

class RequestExecutorService implements EService {
    private final int numberOfThreads;

    public RequestExecutorService(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    @Override
    public ExecutorService initialise() {
        return Executors.newFixedThreadPool(numberOfThreads);
    }
}


interface Executor {
    void execute(Runnable r);

    void shutdown();
}

class RequestExecutor implements Executor {

    private final ExecutorService executor;

    public RequestExecutor(ExecutorService executor) {
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