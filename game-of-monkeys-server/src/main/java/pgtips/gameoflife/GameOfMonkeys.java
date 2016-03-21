package pgtips.gameoflife;

import server.HttpServerRunner;
import server.router.Routes;

import java.io.IOException;

public class GameOfMonkeys {

    private int numberOfMonkeysInNewGame;
    private Thread thread;

    public GameOfMonkeys(int numberOfMonkeysInNewGame) {
        this.numberOfMonkeysInNewGame = numberOfMonkeysInNewGame;
    }

    public static void main(String[] args) {
        new GameOfMonkeys(1).start();
    }

    public void start() {
        Runnable runnable = () -> {
            try {
                Routes routes = new MonkeyGameRoutes(numberOfMonkeysInNewGame);
                HttpServerRunner.runJojonatra(5000, "/tmp/", routes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        thread = new Thread(runnable);
        thread.start();
    }

    public void stop() {
        thread.interrupt();
    }
}
