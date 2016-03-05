package pgtips.gameoflife;

import server.HttpServerRunner;
import server.router.Routes;

import java.io.IOException;

public class GameOfMonkeys {

    public static void main(String[] args) {
        new GameOfMonkeys().start();

    }

    public void start() {
        Runnable runnable = () -> {
            try {
                Routes routes = new MonkeyGameRoutes();
                HttpServerRunner.runJojonatra(5000, "/tmp/", routes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        new Thread(runnable).start();
    }
}
