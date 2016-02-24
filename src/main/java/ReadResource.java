class ReadResource implements Action {
    private final ResourceHandler resourceHandler;

    public ReadResource(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    @Override
    public HttpResponse process(HttpRequest request) {
        byte[] body = resourceHandler.read(request.getRequestUri());
        return HttpResponseBuilder.anHttpResponseBuilder()
                .withStatusCode(StatusCode.OK)
                .withBody(body)
                .build();
    }
}
