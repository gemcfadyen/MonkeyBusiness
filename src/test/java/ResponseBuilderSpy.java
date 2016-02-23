public class ResponseBuilderSpy implements ResponseBuilder {
    private boolean hasBuiltHttpResponse = false;
    private boolean hasStatusCode = false;
    private boolean hasReasonPhrase = false;

    @Override
    public HttpResponse build() {
        hasBuiltHttpResponse = true;
        return new HttpResponse(200, "HTTP/1.1", "OK");
    }

    @Override
    public ResponseBuilder withStatus(int statusCode) {
        hasStatusCode = true;
        return this;
    }

    @Override
    public ResponseBuilder withReasonPhrase(String phrase) {
        hasReasonPhrase = true;
        return this;
    }

    public boolean hasBuiltHttpResponse() {
        return hasBuiltHttpResponse;
    }

    public boolean hasGotStatusCode() {
        return hasStatusCode;
    }

    public boolean hasGotReasonPhrase() {
        return hasReasonPhrase;
    }
}
