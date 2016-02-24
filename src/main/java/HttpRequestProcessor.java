public class HttpRequestProcessor implements RequestProcessor {

    public HttpRequestProcessor() {
    }

    @Override
    public HttpResponse process(HttpRequest httpRequest) {
        HttpResponseBuilder httpResponseBuilder = HttpResponseBuilder.anHttpResponseBuilder();
        if (httpRequest.getRequestUri().equals("/")) {
            httpResponseBuilder.withStatus(200).withReasonPhrase("OK");
        }
        else if (httpRequest.getRequestUri().equals("/form")) {
            if(httpRequest.getMethod().equals("get")) {
                httpResponseBuilder.withBody("My=Data");
            }
            httpResponseBuilder.withStatus(200).withReasonPhrase("OK");
        }
        else if (httpRequest.getRequestUri().equals("/method_options")) {
          httpResponseBuilder.withStatus(200).withReasonPhrase("OK").withAllowMethods(HttpMethods.values());
        }
        else {
           httpResponseBuilder.withStatus(404).withReasonPhrase("Not Found");
        }

        return httpResponseBuilder.build();
    }
}