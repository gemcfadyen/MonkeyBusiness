package server;

import server.messages.HttpRequest;
import server.messages.HttpResponse;

public interface Action {
    HttpResponse process(HttpRequest request);
}
