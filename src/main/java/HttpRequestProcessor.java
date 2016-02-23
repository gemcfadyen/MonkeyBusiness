public class HttpRequestProcessor implements RequestProcessor {
    @Override
    public HttpResponse process(HttpRequest httpRequest) {
        HttpResponse httpResponse = new HttpResponse(404, "HTTP/1.1", "Not Found");
        return httpResponse;
    }
}
