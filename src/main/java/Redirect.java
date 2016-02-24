class Redirect implements Action {

    @Override
    public HttpResponse process(HttpRequest request) {

        return HttpResponseBuilder.anHttpResponseBuilder()
                .withStatus(302)
                .withReasonPhrase("Found")
                .withLocation("http://localhost:5000/")
                .build();
    }
}
