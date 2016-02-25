class Ok implements Action {
    public HttpResponse process(HttpRequest request) {
        return HttpResponseBuilder
                .anHttpResponseBuilder()
                .withStatusCode(StatusCode.OK)
                .build();
    }
}
