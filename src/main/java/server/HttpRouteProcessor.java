package server;

import server.actions.*;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import java.util.HashMap;
import java.util.Map;

import static server.HttpMethods.*;

public class HttpRouteProcessor implements RouteProcessor {
    private Map<RoutingCriteria, Action> routes = new HashMap<>();
    private ResourceHandler resourceHandler;

    public HttpRouteProcessor(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
        configureRoutes();
    }

    private void configureRoutes() {
        routes.put(new RoutingCriteria("/", GET.name()), new ListResourcesInPublicDirectory(resourceHandler));
        routes.put(new RoutingCriteria("/form", GET.name()), new ReadResource(resourceHandler));
        routes.put(new RoutingCriteria("/form", POST.name()), new WriteResource(resourceHandler));
        routes.put(new RoutingCriteria("/form", PUT.name()), new WriteResource(resourceHandler));
        routes.put(new RoutingCriteria("/form", DELETE.name()), new DeleteResource(resourceHandler));
        routes.put(new RoutingCriteria("/method_options", OPTIONS.name()), new MethodOptions());
        routes.put(new RoutingCriteria("/redirect", GET.name()), new Redirect());
        routes.put(new RoutingCriteria("/image.jpeg", GET.name()), new ReadResource(resourceHandler));
        routes.put(new RoutingCriteria("/image.png", GET.name()), new ReadResource(resourceHandler));
        routes.put(new RoutingCriteria("/image.gif", GET.name()), new ReadResource(resourceHandler));
        routes.put(new RoutingCriteria("/file1", GET.name()), new ReadResource(resourceHandler));
    }

    @Override
    public HttpResponse process(HttpRequest httpRequest) {
        System.out.println("Routing key is: " + httpRequest.getRequestUri() + httpRequest.getMethod());
        RoutingCriteria routingCriteria = new RoutingCriteria(httpRequest.getRequestUri(), httpRequest.getMethod());

        return routes.get(routingCriteria) != null ?
                routes.get(routingCriteria).process(httpRequest) :
                new UnknownRoute().process(httpRequest);
    }
}


