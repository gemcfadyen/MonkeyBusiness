public class HttpRequestProcessor implements RequestProcessor {
    @Override
    public HttpResponse process(HttpRequest httpRequest) {

        if (httpRequest.getRequestUri().equals("/")) {
            return new HttpResponse(200, "HTTP/1.1", "OK");
        }

        HttpResponse httpResponse = new HttpResponse(404, "HTTP/1.1", "Not Found");
        return httpResponse;
    }
}