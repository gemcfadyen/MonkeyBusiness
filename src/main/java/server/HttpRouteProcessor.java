package server;

import server.actions.*;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import java.util.HashMap;
import java.util.Map;

import static server.HttpMethods.*;

public class HttpRouteProcessor implements RouteProcessor {
    private Map<RouteKey, Action> routes = new HashMap<>();
    private ResourceHandler resourceHandler;

    public HttpRouteProcessor(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
        configureRoutes();
    }

    private void configureRoutes() {
        routes.put(new RouteKey("/", GET.name()), new Ok());
        routes.put(new RouteKey("/form", GET.name()), new ReadResource(resourceHandler));
        routes.put(new RouteKey("/form", POST.name()), new WriteResource(resourceHandler));
        routes.put(new RouteKey("/form", PUT.name()), new WriteResource(resourceHandler));
        routes.put(new RouteKey("/form", DELETE.name()), new DeleteResource(resourceHandler));
        routes.put(new RouteKey("/method_options", OPTIONS.name()), new MethodOptions());
        routes.put(new RouteKey("/redirect", GET.name()), new Redirect());
        routes.put(new RouteKey("/image.jpeg", GET.name()), new ReadResource(resourceHandler));
        routes.put(new RouteKey("/image.png", GET.name()), new ReadResource(resourceHandler));
        routes.put(new RouteKey("/image.gif", GET.name()), new ReadResource(resourceHandler));
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


