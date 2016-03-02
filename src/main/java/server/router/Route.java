package server.router;

public enum Route {
    HOME("/"),
    FOOBAR("/foobar"),
    REDIRECT("/redirect"),
    LOGS("/logs");

    private final String path;

    Route(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
