import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

class ClientSocket implements HttpSocket {

    private final Socket socket;

    public ClientSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public InputStream getRawHttpRequest() {
        try {
            return socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e); //TODO custom exception handling
        }
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace(); //TODO custom exception handling
        }
    }

    @Override
    public void setHttpResponse(HttpResponse httpResponse) {
        System.out.println("Returning a get 200 request!");

        try {
            socket.getOutputStream().write(httpResponse.formatForClient());
        } catch (IOException e) {
            e.printStackTrace(); //TODO custom exception handling
        }
    }
}
