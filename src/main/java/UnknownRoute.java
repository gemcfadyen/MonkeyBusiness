class UnknownRoute implements Action {
    @Override
    public HttpResponse process(HttpRequest request) {

        return HttpResponseBuilder.anHttpResponseBuilder()
                .withStatus(404)
                .withReasonPhrase("Not Found")
                .build();
    }
}
