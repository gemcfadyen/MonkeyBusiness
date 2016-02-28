package server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private final String host;
    private final int port;
    private final HttpServerSocket serverSocket;
    private final RequestParser requestParser;
    private RouteProcessor httpRouteProcessor;
    private ExecutorServiceFactory maservice;

    public HttpServer(String host, int port,
                      HttpServerSocket serverSocket,
                      RequestParser requestParser,
                      RouteProcessor httpRouteProcessor,
                      FixedThreadPoolExecutorService requestExecutorService) {
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

        ExecutorService executor = maservice.create();

        ProcessClientRequestTask task = new ProcessClientRequestTask(serverSocket.accept(), requestParser, httpRouteProcessor);
        ThreadExecutorService ce = new RequestThreadExecutorService(executor);
        ce.execute(task);
        ce.shutdown();
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