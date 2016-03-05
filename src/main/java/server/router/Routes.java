package server.router;

import server.Action;

import java.util.List;
import java.util.Map;

public interface Routes {
    Map<HttpMethods, List<Action>> routes();
}
