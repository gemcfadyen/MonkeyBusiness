package server.messages;

public enum RequestHeaderProperties {
    PARTIAL_CONTENT_RANGE("Range"),
    AUTHORISATION("Authorization");

    private String property;

    RequestHeaderProperties(String property) {
        this.property = property;
    }

    String getPropertyName() {
        return property;
    }
}
