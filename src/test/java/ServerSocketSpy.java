class ServerSocketSpy implements HttpServerSocket {
    private boolean isAcceptingRequests = false;

    @Override
    public HttpSocket accept() {
        isAcceptingRequests = true;
        return new FakeClient();
    }

    public boolean isAcceptingRequests() {
        return isAcceptingRequests;
    }
}
