public class HttpRequestProcessor implements RequestProcessor {

    private ResourceFinder resourceFinder;

    public HttpRequestProcessor(ResourceFinder resourceFinder) {
        this.resourceFinder = resourceFinder;
    }

    @Override
    public HttpResponse process(HttpRequest httpRequest) {
        System.out.println("Routing the request " + httpRequest.getRequestUri());
        HttpResponseBuilder httpResponseBuilder = HttpResponseBuilder.anHttpResponseBuilder();
        if (httpRequest.getRequestUri().equals("/")) {
            httpResponseBuilder.withStatus(200).withReasonPhrase("OK");
        }
        else if (httpRequest.getRequestUri().equals("/form")) {
            System.out.println("routing at /form with " + httpRequest.getMethod());
            if(httpRequest.getMethod().equals(HttpMethods.GET.name())) {
                System.out.println("GET /FORM");
                String body = resourceFinder.getContentOf(httpRequest.getRequestUri());
                httpResponseBuilder.withBody(body);
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