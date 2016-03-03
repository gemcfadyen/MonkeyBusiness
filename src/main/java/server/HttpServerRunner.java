package server;

import server.filesystem.FileResourceHandler;
import server.httpserver.FixedThreadPoolExecutorService;
import server.httpserver.HttpServer;
import server.httpserver.HttpServerSocket;
import server.messages.HeaderParameterExtractor;
import server.messages.HttpRequestParser;
import server.messages.HttpResponseFormatter;
import server.router.HttpRouteProcessor;
import server.router.RouteLog;
import server.router.Routes;

import java.io.IOException;
import java.net.ServerSocket;

public class HttpServerRunner {

    public static void main(String... args) throws IOException {
        CommandLineArgumentParser commandLineArgumentParser = new CommandLineArgumentParser(System.getProperty("user.home"));

        int port = commandLineArgumentParser.extractPort(args);
        String publicDirectory = commandLineArgumentParser.extractPublicDirectory(args);

        HttpServerSocket httpServerSocket = new HttpServerSocket(new ServerSocket(port), new HttpResponseFormatter());
        FileResourceHandler resourceHandler = new FileResourceHandler(publicDirectory);

        HttpServer httpServer = new HttpServer(
                httpServerSocket,
                new HttpRequestParser(),
                new HttpRouteProcessor(new Routes(resourceHandler, new HeaderParameterExtractor()), new RouteLog(resourceHandler)),
                new FixedThreadPoolExecutorService(4)
        );

        start(httpServer);
    }

    private static void start(HttpServer server) {
        while (true) {
            server.processRequest();
        }
    }
}
