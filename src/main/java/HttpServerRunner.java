import java.io.IOException;
import java.net.ServerSocket;

public class HttpServerRunner {

    public static void main(String... args) throws IOException {
        CommandLineArgumentParser commandLineArgumentParser = new CommandLineArgumentParser();

        String host = "localhost";
        int port = commandLineArgumentParser.extractPort(args);
        String publicDirectory = commandLineArgumentParser.extractPublicDirectory(args);
        System.out.println("[Public Directory] " + publicDirectory);

        HttpServerSocket httpServerSocket = new HttpServerSocket(new ServerSocket(port), new HttpResponseFormatter());

        HttpServer httpServer = new HttpServer(
                host,
                port,
                httpServerSocket,
                new HttpRequestParser(),
                new HttpRequestProcessor(new FileFinder(publicDirectory), new FileResourceWriter(publicDirectory))
        );

        start(httpServer);
    }

    private static void start(HttpServer server) {
        while (true) {
            server.processRequest();
        }
    }
}
