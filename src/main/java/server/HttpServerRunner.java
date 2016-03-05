package server;

import server.filesystem.FileResourceHandler;
import server.httpserver.FixedThreadPoolExecutorService;
import server.httpserver.HttpServer;
import server.httpserver.HttpServerSocket;
import server.messages.HeaderParameterExtractor;
import server.messages.HttpRequestParser;
import server.messages.HttpResponseFormatter;
import server.router.CobSpecRoutes;
import server.router.HttpRouteProcessor;
import server.router.RouteLog;
import server.router.Routes;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;

public class HttpServerRunner {

    public static void main(String... args) throws IOException {

        CommandLineArgumentParser commandLineArgumentParser = new CommandLineArgumentParser(getRepositoryRootDirectory());

        int port = commandLineArgumentParser.extractPort(args);
        String publicDirectory = commandLineArgumentParser.extractPublicDirectory(args);
        ResourceHandler resourceHandler = new FileResourceHandler(publicDirectory);

        runJojonatra(port, publicDirectory, new CobSpecRoutes(resourceHandler, new HeaderParameterExtractor()));
    }

    public static void runJojonatra(int port, String logginDirectory, Routes routes) throws IOException {
        ResourceHandler resourceHandler = new FileResourceHandler(logginDirectory);

        HttpServer httpServer = new HttpServer(
                new HttpServerSocket(new ServerSocket(port), new HttpResponseFormatter()),
                new HttpRequestParser(),
                new HttpRouteProcessor(routes, new RouteLog(resourceHandler)),
                new FixedThreadPoolExecutorService(4)
        );

        start(httpServer);
    }

    private static void start(HttpServer server) {
        while (true) {
            server.processRequest();
        }
    }

    private static String getRepositoryRootDirectory() throws IOException {
        return new File(".").getCanonicalPath();
    }
}
