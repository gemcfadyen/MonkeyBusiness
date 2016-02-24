class Ok implements Action {
    public HttpResponse process(HttpRequest request) {
        return HttpResponseBuilder
                .anHttpResponseBuilder()
                .withStatus(200)
                .withReasonPhrase("OK")
                .build();
    }
}
