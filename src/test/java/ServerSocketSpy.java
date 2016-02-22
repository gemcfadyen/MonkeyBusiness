class ServerSocketSpy implements HttpServerSocket {
    private boolean isAcceptingRequests = false;
    private ClientSpy clientToReturn;

    public ServerSocketSpy(ClientSpy clientSpy) {
        this.clientToReturn = clientSpy;
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
