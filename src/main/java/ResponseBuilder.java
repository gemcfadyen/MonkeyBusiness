interface ResponseBuilder {
    HttpResponse build();
    ResponseBuilder withStatus(int statusCode);
    ResponseBuilder withAllowMethods(String... supportedMethods);
    ResponseBuilder withReasonPhrase(String phrase);
}
