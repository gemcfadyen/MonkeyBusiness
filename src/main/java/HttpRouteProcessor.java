import java.util.HashMap;
import java.util.Map;

public class HttpRouteProcessor implements RouteProcessor {
    private Map<RouteKey, Action> routes = new HashMap<>();
    private ResourceHandler resourceHandler;

    public HttpRouteProcessor(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
        configureRoutes();
    }

    private void configureRoutes() {
        routes.put(new RouteKey("/", HttpMethods.GET.name()), new Ok());
        routes.put(new RouteKey("/form", HttpMethods.GET.name()), new ReadResource(resourceHandler));
        routes.put(new RouteKey("/form", HttpMethods.POST.name()), new WriteResource(resourceHandler));
        routes.put(new RouteKey("/form", HttpMethods.PUT.name()), new WriteResource(resourceHandler));
        routes.put(new RouteKey("/form", HttpMethods.DELETE.name()), new DeleteResource(resourceHandler));
        routes.put(new RouteKey("/method_options", HttpMethods.OPTIONS.name()), new MethodOptions());
        routes.put(new RouteKey("/redirect", HttpMethods.GET.name()), new Redirect());
        routes.put(new RouteKey("/image.jpeg", HttpMethods.GET.name()), new ReadResource(resourceHandler));
        routes.put(new RouteKey("/image.png", HttpMethods.GET.name()), new ReadResource(resourceHandler));
        routes.put(new RouteKey("/image.gif", HttpMethods.GET.name()), new ReadResource(resourceHandler));
    }

    @Override
    public HttpResponse process(HttpRequest httpRequest) {
        System.out.println("Routing key is: " + httpRequest.getRequestUri() + httpRequest.getMethod());
        RouteKey routeKey = new RouteKey(httpRequest.getRequestUri(), httpRequest.getMethod());

        return routes.get(routeKey) != null ?
                routes.get(routeKey).process(httpRequest) :
                new UnknownRoute().process(httpRequest);
    }
}

class DeleteResource implements Action {
    private final ResourceHandler resourceHandler;

    public DeleteResource(ResourceHandler resourceHandler) {
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


