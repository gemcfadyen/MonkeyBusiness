class RouteKey {
    private final String route;
    private final String method;

    public RouteKey(String route, String method) {
        this.route = route;
        this.method = method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RouteKey routeKey = (RouteKey) o;

        if (route != null ? !route.equals(routeKey.route) : routeKey.route != null) return false;
        return !(method != null ? !method.equals(routeKey.method) : routeKey.method != null);

    }

    @Override
    public int hashCode() {
        int result = route != null ? route.hashCode() : 0;
        result = 31 * result + (method != null ? method.hashCode() : 0);
        return result;
    }
}
