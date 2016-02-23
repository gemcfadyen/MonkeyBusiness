public class RequestProcessorSpy implements RequestProcessor {
    private boolean hasProcessed = false;

    public boolean hasProcessed() {
        return hasProcessed;
    }

    @Override
    public HttpResponse process(HttpRequest httpRequest) {
        hasProcessed = true;
        return new HttpResponse(200, "HTTP/1.1", "OK");
    }
}
