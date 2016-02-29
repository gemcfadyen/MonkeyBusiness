package server.messages;

public enum Delimiter {
    COMMA(","), BREAK("<br>");

    private String delimiter;

    Delimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String get() {
        return delimiter;
    }

}
