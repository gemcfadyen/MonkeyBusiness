package server;

public interface ResponseFormatter {
    byte[] format(HttpResponse response);
}
