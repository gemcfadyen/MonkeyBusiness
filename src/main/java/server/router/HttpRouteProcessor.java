package server.router;

import server.Action;
import server.ResourceHandler;
import server.RouteProcessor;
import server.actions.*;
import server.messages.HeaderParameterExtractor;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import java.util.HashMap;
import java.util.Map;

import static server.actions.EtagGenerationAlgorithm.SHA_1;
import static server.router.HttpMethods.*;
import static server.router.Route.*;

public class HttpRouteProcessor implements RouteProcessor {
    private Map<RoutingCriteria, Action> routes = new HashMap<>();
    private ResourceHandler resourceHandler;

    public HttpRouteProcessor(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
        configureRoutes();
    }

    @Override
    public HttpResponse process(HttpRequest httpRequest) {
        if (isBogusMethod(httpRequest)) {
            return methodNotSupported(httpRequest);
        } else if (supportedRoute(httpRequest)) {
            return processRoute(httpRequest);
        } else {
            return fourOFour(httpRequest);
        }
    }

    private void configureRoutes() {
        routes.put(new RoutingCriteria(HOME, GET), new ListResourcesInPublicDirectory(resourceHandler));
        routes.put(new RoutingCriteria(FORM, GET), new ReadResource(resourceHandler));
        routes.put(new RoutingCriteria(FORM, POST), new WriteResource(resourceHandler));
        routes.put(new RoutingCriteria(FORM, PUT), new WriteResource(resourceHandler));
        routes.put(new RoutingCriteria(FORM, DELETE), new DeleteResource(resourceHandler));
        routes.put(new RoutingCriteria(METHOD_OPTIONS, OPTIONS), new MethodOptions());
        routes.put(new RoutingCriteria(REDIRECT, GET), new Redirect());
        routes.put(new RoutingCriteria(IMAGE_JPEG, GET), new ReadResource(resourceHandler));
        routes.put(new RoutingCriteria(IMAGE_PNG, GET), new ReadResource(resourceHandler));
        routes.put(new RoutingCriteria(IMAGE_GIF, GET), new ReadResource(resourceHandler));
        routes.put(new RoutingCriteria(FILE, GET), new ReadResource(resourceHandler));
        routes.put(new RoutingCriteria(FILE, PUT), new MethodNotAllowed(new HeaderParameterExtractor()));
        routes.put(new RoutingCriteria(TEXT_FILE, GET), new ReadResource(resourceHandler));
        routes.put(new RoutingCriteria(TEXT_FILE, POST), new MethodNotAllowed(new HeaderParameterExtractor()));
        routes.put(new RoutingCriteria(PARAMETERS, GET), new IncludeParametersInBody());
        routes.put(new RoutingCriteria(LOGS, GET), new Authorisation(new ReadResource(resourceHandler), new HeaderParameterExtractor()));
        routes.put(new RoutingCriteria(LOG, GET), new LogRequest(resourceHandler));
        routes.put(new RoutingCriteria(THESE, PUT), new LogRequest(resourceHandler));
        routes.put(new RoutingCriteria(REQUESTS, HEAD), new LogRequest(resourceHandler));
        routes.put(new RoutingCriteria(PARTIAL_CONTENT, GET), new PartialContent(resourceHandler, new HeaderParameterExtractor()));
        routes.put(new RoutingCriteria(PATCH_CONTENT, GET), new ReadResource(resourceHandler));
        routes.put(new RoutingCriteria(PATCH_CONTENT, PATCH), new PatchResource(resourceHandler, new EtagGenerator(SHA_1), new HeaderParameterExtractor()));
    }

    private boolean isBogusMethod(HttpRequest httpRequest) {
        return isBogus(httpRequest.getMethod());
    }

    private HttpResponse methodNotSupported(HttpRequest httpRequest) {
        return new MethodNotAllowed(new HeaderParameterExtractor()).process(httpRequest);
    }

    private boolean supportedRoute(HttpRequest httpRequest) {
        return Route.isSupported(httpRequest.getRequestUri())
                && routes.get(routingCriteria(httpRequest)) != null;
    }

    private HttpResponse processRoute(HttpRequest httpRequest) {
        return routes.get(routingCriteria(httpRequest)).process(httpRequest);
    }

    private RoutingCriteria routingCriteria(HttpRequest httpRequest) {
        return new RoutingCriteria(Route.getRouteFor(httpRequest.getRequestUri()), HttpMethods.valueOf(httpRequest.getMethod()));
    }

    private HttpResponse fourOFour(HttpRequest httpRequest) {
        return new UnknownRoute().process(httpRequest);
    }
}
