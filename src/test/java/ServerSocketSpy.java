class ServerSocketSpy implements HttpServerSocket {
    private boolean isAcceptingRequests = false;
    private FakeClient clientToReturn;

    public ServerSocketSpy(FakeClient fakeClient) {
        this.clientToReturn = fakeClient;
    }

    @Override
    public HttpSocket accept() {
        isAcceptingRequests = true;
        return clientToReturn;
    }

    public boolean isAcceptingRequests() {
        return isAcceptingRequests;
    }
}
