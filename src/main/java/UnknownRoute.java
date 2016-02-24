class UnknownRoute implements Action {
    @Override
    public HttpResponse process(HttpRequest request) {

        return HttpResponseBuilder.anHttpResponseBuilder()
                .withStatusCode(StatusCode.NOT_FOUND)
                .build();
    }
}
