public class HttpRequestProcessor implements RequestProcessor {

    private ResourceFinder resourceFinder;
    private ResourceWriter resourceWriter;

    public HttpRequestProcessor(ResourceFinder resourceFinder, ResourceWriter resourceWriter) {
        this.resourceFinder = resourceFinder;
        this.resourceWriter = resourceWriter;
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
                System.out.println("BODY FROM THE GET IS " + body);
                httpResponseBuilder.withBody(body);
            }

            if(httpRequest.getMethod().equals(HttpMethods.POST.name())) {
                System.out.println("POST /FORM");
                resourceWriter.write(httpRequest.getRequestUri(), httpRequest.getBody());
                httpResponseBuilder.withBody(httpRequest.getBody());
            }

            if(httpRequest.getMethod().equals(HttpMethods.PUT.name())) {
                System.out.println("PUT /FORM");
                resourceWriter.write(httpRequest.getRequestUri(), httpRequest.getBody());
                httpResponseBuilder.withBody(httpRequest.getBody());
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