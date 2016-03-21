package pgtips.gameoflife;


import server.Action;
import server.router.HttpMethods;
import server.router.Routes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static server.router.HttpMethods.GET;

public class MonkeyGameRoutes implements Routes {
    private int numberOfMonkeys;

    public MonkeyGameRoutes(int numberOfMonkeys) {
        this.numberOfMonkeys = numberOfMonkeys;
    }

    @Override
    public Map<HttpMethods, List<Action>> routes() {
        Map<HttpMethods, List<Action>> routes = new HashMap<>();
        routes.put(GET, Arrays.asList(new MonkeyBusiness(numberOfMonkeys)));
        return routes;
    }
}
