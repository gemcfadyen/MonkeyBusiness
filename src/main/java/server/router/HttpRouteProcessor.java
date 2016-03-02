package server.router;

import server.Action;
import server.RouteProcessor;
import server.actions.MethodNotAllowed;
import server.actions.UnknownRoute;
import server.messages.HeaderParameterExtractor;
import server.messages.HttpRequest;
import server.messages.HttpResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static server.router.HttpMethods.isBogus;

public class HttpRouteProcessor implements RouteProcessor {
    private Map<HttpMethods, List<Action>> routes = new HashMap<>();

    public HttpRouteProcessor(Routes routes) {
        this.routes = routes.routes();
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
