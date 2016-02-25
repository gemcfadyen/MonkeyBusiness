package server;

class DeleteResource implements Action {
    private final ResourceHandler resourceHandler;

    public DeleteResource(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }

    @Override
    public HttpResponse process(HttpRequest request) {
        System.out.println("DELETE/FORM");
        resourceHandler.delete(request.getRequestUri());

        return HttpResponseBuilder.anHttpResponseBuilder()
                .withStatusCode(StatusCode.OK)
                .build();
    }
}
