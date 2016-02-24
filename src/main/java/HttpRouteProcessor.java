import java.util.HashMap;
import java.util.Map;

public class HttpRouteProcessor implements RouteProcessor {
    private Map<RouteKey, RequestProcessor> routes = new HashMap<>();
    private ResourceHandler resourceHandler;

    public HttpRouteProcessor(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
        configureRoutes();
    }

    private void configureRoutes() {
        routes.put(new RouteKey("/", HttpMethods.GET.name()), new OkResponse());
        routes.put(new RouteKey("/form", HttpMethods.GET.name()), new ReadResourceResponse(resourceHandler));
        routes.put(new RouteKey("/form", HttpMethods.POST.name()), new WriteResourceResponse(resourceHandler));
        routes.put(new RouteKey("/form", HttpMethods.PUT.name()), new WriteResourceResponse(resourceHandler));
        routes.put(new RouteKey("/form", HttpMethods.DELETE.name()), new DeleteContentResponse(resourceHandler));
        routes.put(new RouteKey("/method_options", HttpMethods.OPTIONS.name()), new MethodOptionsResponse());
        routes.put(new RouteKey("/redirect", HttpMethods.GET.name()), new RedirectResponse());
        routes.put(new RouteKey("/image.jpeg", HttpMethods.GET.name()), new ReadResourceResponse(resourceHandler));
        routes.put(new RouteKey("/image.png", HttpMethods.GET.name()), new ReadResourceResponse(resourceHandler));
        routes.put(new RouteKey("/image.gif", HttpMethods.GET.name()), new ReadResourceResponse(resourceHandler));
    }

    @Override
    public HttpResponse process(HttpRequest httpRequest) {
        System.out.println("Routing key is: " + httpRequest.getRequestUri() + httpRequest.getMethod());
        RouteKey routeKey = new RouteKey(httpRequest.getRequestUri(), httpRequest.getMethod());

        return routes.get(routeKey) != null ?
                routes.get(routeKey).process(httpRequest) :
                new UnknownRouteResponse().process(httpRequest);
    }
}

interface RequestProcessor {

    HttpResponse process(HttpRequest request);
}

class OkResponse implements RequestProcessor {
    public HttpResponse process(HttpRequest request) {
        return HttpResponseBuilder
                .anHttpResponseBuilder()
                .withStatus(200)
                .withReasonPhrase("OK")
                .build();
    }
}

class WriteResourceResponse implements RequestProcessor {
    private final ResourceHandler resourceHandler;

    public WriteResourceResponse(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    @Override
    public HttpResponse process(HttpRequest request) {
        System.out.println("PUT /FORM");
        resourceHandler.write(request.getRequestUri(), request.getBody());

        return HttpResponseBuilder.anHttpResponseBuilder()
                .withStatus(200)
                .withReasonPhrase("OK")
                .withBody(request.getBody().getBytes())
                .build();
    }
}

class DeleteContentResponse implements RequestProcessor {
    private final ResourceHandler resourceHandler;

    public DeleteContentResponse(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    @Override
    public HttpResponse process(HttpRequest request) {
        System.out.println("DELETE/FORM");
        resourceHandler.delete(request.getRequestUri());

        return HttpResponseBuilder.anHttpResponseBuilder()
                .withStatus(200)
                .withReasonPhrase("OK")
                .build();
    }
}


class MethodOptionsResponse implements RequestProcessor {

    @Override
    public HttpResponse process(HttpRequest request) {
        return HttpResponseBuilder.anHttpResponseBuilder()
                .withStatus(200)
                .withReasonPhrase("OK")
                .withAllowMethods(HttpMethods.values())
                .build();
    }
}

class RedirectResponse implements RequestProcessor {

    @Override
    public HttpResponse process(HttpRequest request) {

        return HttpResponseBuilder.anHttpResponseBuilder()
                .withStatus(302)
                .withReasonPhrase("Found")
                .withLocation("http://localhost:5000/")
                .build();
    }
}

class ReadResourceResponse implements RequestProcessor {
    private final ResourceHandler resourceHandler;

    public ReadResourceResponse(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    @Override
    public HttpResponse process(HttpRequest request) {
        byte[] body = resourceHandler.read(request.getRequestUri());
        return HttpResponseBuilder.anHttpResponseBuilder()
                .withStatus(200)
                .withReasonPhrase("OK")
                .withBody(body)
                .build();
    }
}

class UnknownRouteResponse implements RequestProcessor {
    @Override
    public HttpResponse process(HttpRequest request) {

        return HttpResponseBuilder.anHttpResponseBuilder()
                .withStatus(404)
                .withReasonPhrase("Not Found")
                .build();
    }
}