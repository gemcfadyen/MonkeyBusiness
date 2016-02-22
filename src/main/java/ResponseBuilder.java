interface ResponseBuilder {
    HttpResponse build();
    ResponseBuilder withStatus(int statusCode);
    ResponseBuilder withReasonPhrase(String phrase);
}
