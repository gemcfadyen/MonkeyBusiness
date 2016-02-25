package server;

public interface RouteProcessor {
    HttpResponse process(HttpRequest httpRequest);
}
