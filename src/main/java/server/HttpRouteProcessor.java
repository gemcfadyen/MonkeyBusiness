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
        routes.put(new RoutingCriteria("/", GET), new ListResourcesInPublicDirectory(resourceHandler));
        routes.put(new RoutingCriteria("/form", GET), new ReadResource(resourceHandler));
        routes.put(new RoutingCriteria("/form", POST), new WriteResource(resourceHandler));
        routes.put(new RoutingCriteria("/form", PUT), new WriteResource(resourceHandler));
        routes.put(new RoutingCriteria("/form", DELETE), new DeleteResource(resourceHandler));
        routes.put(new RoutingCriteria("/method_options", OPTIONS), new MethodOptions());
        routes.put(new RoutingCriteria("/redirect", GET), new Redirect());
        routes.put(new RoutingCriteria("/image.jpeg", GET), new ReadResource(resourceHandler));
        routes.put(new RoutingCriteria("/image.png", GET), new ReadResource(resourceHandler));
        routes.put(new RoutingCriteria("/image.gif", GET), new ReadResource(resourceHandler));
        routes.put(new RoutingCriteria("/file1", GET), new ReadResource(resourceHandler));
        routes.put(new RoutingCriteria("/file1", PUT), new MethodNotAllowed());
        routes.put(new RoutingCriteria("/text-file.txt", GET), new ReadResource(resourceHandler));
        routes.put(new RoutingCriteria("/text-file.txt", POST), new MethodNotAllowed());
        routes.put(new RoutingCriteria("/parameters", GET), new IncludeParametersInBody());
        routes.put(new RoutingCriteria("/logs", GET), new Authorisation());
    }

    @Override
    public HttpResponse process(HttpRequest httpRequest) {
        System.out.println("Routing key is: " + httpRequest.getRequestUri() + httpRequest.getMethod());
        System.out.println("Check what to do with it...");
        if (isBogusMethod(httpRequest)) {
            return new MethodNotAllowed().process(httpRequest);
        } else if (supportedRoute(httpRequest)) {
            return routes.get(routingCriteria(httpRequest)).process(httpRequest);
        } else {
            System.out.println("Unknown route selected");
            return new UnknownRoute().process(httpRequest);
        }
    }

    private boolean isBogusMethod(HttpRequest httpRequest) {
        return isBogus(httpRequest.getMethod());

    }

    private RoutingCriteria routingCriteria(HttpRequest httpRequest) {
        System.out.println("Getting the request uri: " + httpRequest.getRequestUri() + " method " + httpRequest.getMethod());
        return new RoutingCriteria(httpRequest.getRequestUri(), HttpMethods.valueOf(httpRequest.getMethod()));
    }

    private boolean supportedRoute(HttpRequest httpRequest) {
        return routes.get(routingCriteria(httpRequest)) != null;
    }
}

