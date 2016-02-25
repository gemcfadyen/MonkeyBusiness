class Redirect implements Action {

    @Override
    public HttpResponse process(HttpRequest request) {

        return HttpResponseBuilder.anHttpResponseBuilder()
                .withStatusCode(StatusCode.FOUND)
                .withLocation("http://localhost:5000/")
                .build();
    }
}
