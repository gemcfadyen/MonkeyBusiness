public class RequestProcessorSpy implements RequestProcessor {
    private boolean hasProcessed = false;

    public boolean hasProcessed() {
        return hasProcessed;
    }

    @Override
    public HttpResponse process(HttpRequest httpRequest) {
        hasProcessed = true;
        return HttpResponseBuilder.anHttpResponseBuilder()
                .withStatus(200)
                .withReasonPhrase("OK")
                .build();
    }
}
