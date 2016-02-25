package server;

public interface Action {
    HttpResponse process(HttpRequest request);
}
