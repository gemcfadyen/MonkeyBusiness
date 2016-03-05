package pgtips.gamoflife;

import server.HttpServerRunner;

import java.io.IOException;

public class GameOfMonkeys {
    public void start() {
        Runnable runnable = () -> {
            try {
                HttpServerRunner.main();
            } catch (IOException e) {
                e.printStackTrace();
            }
        };

        new Thread(runnable).start();
    }
}
