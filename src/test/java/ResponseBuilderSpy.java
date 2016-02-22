class ResponseBuilderSpy implements ResponseBuilder {
    private boolean hasBuiltHttpResponse;

    @Override
    public HttpResponse build() {
        hasBuiltHttpResponse = true;
        return new HttpResponse();
    }

    public boolean hasBuiltHttpResponse() {
        return hasBuiltHttpResponse;
    }
}
