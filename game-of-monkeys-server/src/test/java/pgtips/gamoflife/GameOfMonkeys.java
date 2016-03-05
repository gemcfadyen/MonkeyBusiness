package pgtips.gamoflife;

import server.Action;
import server.HttpServerRunner;
import server.ResourceHandler;
import server.actions.MonkeyBusiness;
import server.messages.HeaderParameterExtractor;
import server.router.HttpMethods;
import server.router.Routes;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static server.router.HttpMethods.GET;

public class GameOfMonkeys {
    public void start() {
        Runnable runnable = () -> {
            try {
                Routes routes = new Routes(null, null) {
                    protected Map<HttpMethods, List<Action>> configure(ResourceHandler resourceHandler, HeaderParameterExtractor headerParameterExtractor) {
                        HashMap<HttpMethods, List<Action>> routes = new HashMap<>();
                        routes.put(GET, Arrays.asList(new MonkeyBusiness()));
                        return routes;
                    }
                };
                HttpServerRunner.runJojonatra(5000, "/tmp/", routes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        new Thread(runnable).start();
    }
}
