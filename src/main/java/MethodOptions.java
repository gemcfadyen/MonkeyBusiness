class MethodOptions implements Action {

    @Override
    public HttpResponse process(HttpRequest request) {
        return HttpResponseBuilder.anHttpResponseBuilder()
                .withStatusCode(StatusCode.OK)
                .withAllowMethods(HttpMethods.values())
                .build();
    }
}
