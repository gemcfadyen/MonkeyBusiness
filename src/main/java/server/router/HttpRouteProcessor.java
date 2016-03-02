package server.router;

import server.Action;
import server.ResourceHandler;
import server.RouteProcessor;
import server.actions.*;
import server.messages.HeaderParameterExtractor;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static server.actions.EtagGenerationAlgorithm.SHA_1;
import static server.router.HttpMethods.*;

public class HttpRouteProcessor implements RouteProcessor {
    private final HeaderParameterExtractor headerParameterExtractor;
    private Map<HttpMethods, List<Action>> routes = new HashMap<>();
    private ResourceHandler resourceHandler;

    public HttpRouteProcessor(ResourceHandler resourceHandler, HeaderParameterExtractor headerParameterExtractor) {
        this.resourceHandler = resourceHandler;
        this.headerParameterExtractor = headerParameterExtractor;
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
        routes.put(GET, asList(
                new UnknownRoute(),
                new ListResourcesInPublicDirectory(resourceHandler),
                new Redirect(),
                new Authorisation(new ReadResource(resourceHandler), new HeaderParameterExtractor()),
                new IncludeParametersInBody(),
                new PartialContent(resourceHandler, headerParameterExtractor),
                new ReadResource(resourceHandler)));

        routes.put(POST, asList(
                new MethodNotAllowed(headerParameterExtractor),
                new WriteResource(resourceHandler)
        ));

        routes.put(PUT, asList(
                new MethodNotAllowed(headerParameterExtractor),
                new WriteResource(resourceHandler)));

        routes.put(DELETE, singletonList(new DeleteResource(resourceHandler)));
        routes.put(OPTIONS, singletonList(new ReadResource(resourceHandler)));
        routes.put(PATCH, singletonList(new PatchResource(resourceHandler, new EtagGenerator(SHA_1), headerParameterExtractor)));
        routes.put(HEAD, singletonList(new LogRequest(resourceHandler)));
    }

    private boolean isBogusMethod(HttpRequest httpRequest) {
        return isBogus(httpRequest.getMethod());
    }

    private HttpResponse methodNotSupported(HttpRequest httpRequest) {
        return new MethodNotAllowed(new HeaderParameterExtractor()).process(httpRequest);
    }

    private boolean supportedRoute(HttpRequest httpRequest) {
        return routes.get(HttpMethods.valueOf(httpRequest.getMethod())) != null;
    }

    private HttpResponse processRoute(HttpRequest httpRequest) {
        List<Action> actions = routes.get(HttpMethods.valueOf(httpRequest.getMethod()));
        for (Action action : actions) {
            if (action.isEligible(httpRequest)) {
                return action.process(httpRequest);
            }
        }
        return fourOFour(httpRequest);
    }

    private HttpResponse fourOFour(HttpRequest httpRequest) {
        return new UnknownRoute().process(httpRequest);
    }
}
