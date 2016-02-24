class MethodOptions implements Action {

    @Override
    public HttpResponse process(HttpRequest request) {
        return HttpResponseBuilder.anHttpResponseBuilder()
                .withStatus(200)
                .withReasonPhrase("OK")
                .withAllowMethods(HttpMethods.values())
                .build();
    }
}
